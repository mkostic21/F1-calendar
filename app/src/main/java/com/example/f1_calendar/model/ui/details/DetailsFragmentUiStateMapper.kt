package com.example.f1_calendar.model.ui.details

import com.example.f1_calendar.model.domain.Circuit


object DetailsFragmentUiStateMapper {
    fun mapLat(circuit: Circuit): String {
        return circuit.location.lat
    }

    fun mapLong(circuit: Circuit): String {
        return circuit.location.long
    }

    fun mapUrl(circuit: Circuit): String {
        return circuit.url
    }
}