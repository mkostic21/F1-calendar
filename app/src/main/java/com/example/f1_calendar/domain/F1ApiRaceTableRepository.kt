package com.example.f1_calendar.domain

import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class F1ApiRaceTableRepository @Inject constructor(
    private val f1Api: F1Api,
    // private val roomDatabase: F1Database
) : RaceTableRepository {
    override fun getRaceTable(season: String): Single<RaceTable> {
        return f1Api.getSeasonData(season = season).map { response ->
            RaceTable(
                races = F1ApiDomainMapper.mapRaces(response.MRData.raceTable),
                season = response.MRData.raceTable.season
            )
        }
    }

    override fun getCircuit(season: String, circuitId: String): Single<Circuit> {
        return f1Api.getSeasonData(season = season).map { response ->
            F1ApiDomainMapper.mapCircuit(response.MRData.raceTable.Races, circuitId)!!
        }
    }


}