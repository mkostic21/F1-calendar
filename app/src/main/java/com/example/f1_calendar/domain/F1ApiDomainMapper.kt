package com.example.f1_calendar.domain

import com.example.f1_calendar.model.api.Circuit
import com.example.f1_calendar.model.api.Qualifying
import com.example.f1_calendar.model.api.SecondPractice
import com.example.f1_calendar.model.api.Sprint
import com.example.f1_calendar.model.domain.*
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object F1ApiDomainMapper {
    fun mapRaces(raceTable: com.example.f1_calendar.model.api.RaceTable): List<Race> {
        val domainRaces = mutableListOf<Race>()
        for (race in raceTable.Races)
            domainRaces.add(
                Race(
                    eventType = "Race",
                    circuit = mapCircuit(race.Circuit),
                    firstPractice = mapFirstPractice(race.FirstPractice),
                    secondPractice = mapSecondPractice(race.SecondPractice),
                    thirdPractice = mapThirdPractice(race.ThirdPractice),
                    qualifying = mapQualifying(race.Qualifying),
                    sprint = mapSprint(race.Sprint),
                    dateTime = parseDateTime(race.date, race.time),
                    raceName = race.raceName,
                    round = race.round,
                )
            )
        return domainRaces
    }

    private fun mapCircuit(circuit: Circuit): com.example.f1_calendar.model.domain.Circuit {
        return Circuit(
            location = mapCircuitLocation(circuit.Location),
            circuitId = circuit.circuitId,
            circuitName = circuit.circuitName,
            url = circuit.url
        )
    }

    fun mapCircuit(
        raceTable: RaceTable,
        circuitId: String
    ): com.example.f1_calendar.model.domain.Circuit {
        for (race in raceTable.races) {
            if (race.circuit.circuitId == circuitId) {
                return mapCircuit(race.circuit)
            }
        }
        // todo (david): figure out best course of action and report back
        return mapCircuit(raceTable.races[0].circuit)
    }

    private fun mapSprint(sprint: Sprint?): com.example.f1_calendar.model.domain.Sprint? {
        return if (sprint != null) Sprint(
            eventType = "Sprint",
            parseDateTime(sprint.date, sprint.time)
        ) else null

    }

    private fun mapQualifying(qualifying: Qualifying?): com.example.f1_calendar.model.domain.Qualifying? {
        return if (qualifying != null) Qualifying(
            eventType = "Qualifying",
            parseDateTime(qualifying.date, qualifying.time)
        ) else null
    }

    private fun mapThirdPractice(thirdPractice: com.example.f1_calendar.model.api.ThirdPractice?): ThirdPractice? {
        return if (thirdPractice != null) ThirdPractice(
            eventType = "Third Practice",
            parseDateTime(thirdPractice.date, thirdPractice.time)
        ) else null
    }

    private fun mapSecondPractice(secondPractice: SecondPractice?): com.example.f1_calendar.model.domain.SecondPractice? {
        return if (secondPractice != null) SecondPractice(
            eventType = "Second Practice",
            parseDateTime(secondPractice.date, secondPractice.time)
        ) else null
    }

    private fun mapCircuit(circuit: com.example.f1_calendar.model.domain.Circuit): com.example.f1_calendar.model.domain.Circuit {
        return Circuit(
            location = circuit.location,
            circuitId = circuit.circuitId,
            circuitName = circuit.circuitName,
            url = circuit.url
        )
    }

    private fun mapCircuitLocation(location: com.example.f1_calendar.model.api.Location): Location {
        return Location(
            country = location.country,
            lat = location.lat,
            long = location.long,
            locality = location.locality
        )
    }

    private fun mapFirstPractice(firstPractice: com.example.f1_calendar.model.api.FirstPractice?): FirstPractice? {
        return if (firstPractice != null) FirstPractice(
            eventType = "First Practice",
            parseDateTime(firstPractice.date, firstPractice.time)
        ) else null
    }

    private fun parseDateTime(date: String?, time: String?): ZonedDateTime {
        return when {
            time.isNullOrBlank() -> {
                LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                    .atStartOfDay(ZoneOffset.UTC)
            }
            else -> {
                ZonedDateTime.parse(
                    "${date}T$time",
                    DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)
                )
            }
        }
    }
}