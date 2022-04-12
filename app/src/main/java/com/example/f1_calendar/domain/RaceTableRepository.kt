package com.example.f1_calendar.domain

import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Single

interface RaceTableRepository {
    fun getCurrentSeasonRaceTable(): Single<RaceTable>
    fun getCircuit(circuitId: String): Single<Circuit>
}