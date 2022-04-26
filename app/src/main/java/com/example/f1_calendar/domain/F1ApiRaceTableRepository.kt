package com.example.f1_calendar.domain

import android.util.Log
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import com.example.f1_calendar.room.RaceTableDao
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class F1ApiRaceTableRepository @Inject constructor(
    private val f1Api: F1Api,
    private val roomDatabase: RaceTableDao
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

    override fun getCircuit(season: String, circuitId: String): Maybe<Circuit> {
        return getRaceTable(season = season).flatMapMaybe { raceTable ->
            val circuit = F1ApiDomainMapper.mapCircuit(
                raceTable = raceTable,
                circuitId = circuitId
            )
            if (circuit == null) {
                Maybe.empty()
            } else {
                Maybe.just(circuit)
            }
        }
    }

    private fun getRacesFromDatabase(season: String): Single<RaceTable> {
        return roomDatabase.getRacesBySeason(season = season)
    }

    private fun fetchFromApiAndSaveToDb(season: String): Single<RaceTable> {
        return f1Api.getSeasonData(season = season).map { response ->
            Log.d("response", "fetchFromApiAndSaveToDb: from API")
            RaceTable(
                races = F1ApiDomainMapper.mapRaces(response.MRData.raceTable),
                season = response.MRData.raceTable.season
            )
        }.flatMap { raceTable ->
            roomDatabase
                .insertRaceTable(raceTable = raceTable)
                .toSingleDefault(raceTable)
        }
    }
}

private class RacesEmptyException : RuntimeException()