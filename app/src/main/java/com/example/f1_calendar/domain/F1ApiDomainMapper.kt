package com.example.f1_calendar.domain

import com.example.f1_calendar.model.api.Circuit
import com.example.f1_calendar.model.api.Qualifying
import com.example.f1_calendar.model.api.SecondPractice
import com.example.f1_calendar.model.api.Sprint
import com.example.f1_calendar.model.domain.FirstPractice
import com.example.f1_calendar.model.domain.Location
import com.example.f1_calendar.model.domain.Race
import com.example.f1_calendar.model.domain.ThirdPractice
import java.time.LocalDate
import java.time.ZoneId
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

    fun mapCircuit(
        races: List<com.example.f1_calendar.model.api.Race>,
        circuitId: String
    ): com.example.f1_calendar.model.domain.Circuit? {
        for (race in races) {
            if (race.Circuit.circuitId == circuitId) {
                return mapCircuit(race.Circuit)
            }
        }
        return null
    }

    private fun mapSprint(sprint: Sprint?): com.example.f1_calendar.model.domain.Sprint? {
        return if (sprint != null) com.example.f1_calendar.model.domain.Sprint(
            eventType = "Sprint",
            parseDateTime(sprint.date, sprint.time)
        ) else null

    }

    private fun mapQualifying(qualifying: Qualifying?): com.example.f1_calendar.model.domain.Qualifying? {
        return if (qualifying != null) com.example.f1_calendar.model.domain.Qualifying(
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
        return if (secondPractice != null) com.example.f1_calendar.model.domain.SecondPractice(
            eventType = "Second Practice",
            parseDateTime(secondPractice.date, secondPractice.time)
        ) else null
    }

    private fun mapCircuit(circuit: Circuit): com.example.f1_calendar.model.domain.Circuit {
        return com.example.f1_calendar.model.domain.Circuit(
            location = mapCircuitLocation(circuit.Location),
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
                //todo: set date only
                //ZonedDateTime.parse("${date}T00:00:00Z", DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC))
                LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay(ZoneId.systemDefault())
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