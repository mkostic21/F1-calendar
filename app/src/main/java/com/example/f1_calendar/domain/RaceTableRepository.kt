package com.example.f1_calendar.domain

import com.example.f1_calendar.model.domain.Circuit
import com.example.f1_calendar.model.domain.RaceTable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface RaceTableRepository {
    fun getRaceTable(season: String): Single<RaceTable>
    fun getCircuit(season: String, circuitId: String): Maybe<Circuit>
}