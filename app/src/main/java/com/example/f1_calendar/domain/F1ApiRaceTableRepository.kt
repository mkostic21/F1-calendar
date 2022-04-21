package com.example.f1_calendar.domain

import android.util.Log
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import com.example.f1_calendar.room.F1Database
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class F1ApiRaceTableRepository @Inject constructor(
    private val f1Api: F1Api,
    // todo: inject race table dao, not whole database
    private val roomDatabase: F1Database
) : RaceTableRepository {
    override fun getRaceTable(season: String): Single<RaceTable> {
        return getRacesFromDatabase(season = season)
            .doOnSuccess { raceTable ->
                if (raceTable.races.isEmpty()) {
                    throw RacesEmptyException()
                }
            }
            .onErrorResumeNext {
                Log.d("response", "getRaceTable: ${it.localizedMessage}")
                fetchFromApiAndSaveToDb(season = season)
            }
    }

    override fun getCircuit(season: String, circuitId: String): Single<Circuit> {
        return getRaceTable(season = season).map { raceTable ->
            F1ApiDomainMapper.mapCircuit(raceTable = raceTable, circuitId = circuitId)
        }
    }

    private fun getRacesFromDatabase(season: String): Single<RaceTable> {
        return roomDatabase.getRaceTableDao().getRacesBySeason(season = season)
    }

    private fun fetchFromApiAndSaveToDb(season: String): Single<RaceTable> {
        return f1Api.getSeasonData(season = season).map { response ->
            Log.d("response", "fetchFromApiAndSaveToDb: from API")
            RaceTable(
                races = F1ApiDomainMapper.mapRaces(response.MRData.raceTable),
                season = response.MRData.raceTable.season
            )
        }.flatMap { raceTable ->
            roomDatabase.getRaceTableDao()
                .insertRaceTable(raceTable = raceTable)
                .toSingleDefault(raceTable)
        }
    }
}

// todo: make private
class RacesEmptyException : RuntimeException()