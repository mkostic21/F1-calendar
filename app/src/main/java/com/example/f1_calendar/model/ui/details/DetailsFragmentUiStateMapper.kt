package com.example.f1_calendar.model.ui.details

import com.example.f1_calendar.model.domain.Circuit


object DetailsFragmentUiStateMapper {

    fun map(circuit: Circuit): DetailsFragmentUiState.Success{
        return DetailsFragmentUiState.Success(
            lat = mapLat(circuit = circuit),
            long = mapLong(circuit = circuit),
            url = mapUrl(circuit = circuit)
        )
    }
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