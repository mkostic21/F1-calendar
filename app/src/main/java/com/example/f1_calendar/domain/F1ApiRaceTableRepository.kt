package com.example.f1_calendar.domain

import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Single

class F1ApiRaceTableRepository(
    private val f1Api: F1Api,
    // private val roomDatabase: F1Database
) : RaceTableRepository {
    override fun getCurrentSeasonRaceTable(): Single<RaceTable> {
        return f1Api.getCurrentSeasonData().map { response ->
            RaceTable(
                races = F1ApiDomainMapper.mapRaces(response.MRData.raceTable),
                season = response.MRData.raceTable.season
            )
        }
    }

    override fun getCircuit(circuitId: String): Single<Circuit> {
        return f1Api.getCurrentSeasonData().map { response ->
            F1ApiDomainMapper.mapCircuit(response.MRData.raceTable.Races, circuitId)!!
        }
    }


}