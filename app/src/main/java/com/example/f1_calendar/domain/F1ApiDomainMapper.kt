package com.example.f1_calendar.domain

import com.example.f1_calendar.model.api.Circuit
import com.example.f1_calendar.model.api.Qualifying
import com.example.f1_calendar.model.api.SecondPractice
import com.example.f1_calendar.model.api.Sprint
import com.example.f1_calendar.model.domain.FirstPractice
import com.example.f1_calendar.model.domain.Location
import com.example.f1_calendar.model.domain.Race
import com.example.f1_calendar.model.domain.ThirdPractice
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

    private fun mapSprint(sprint: Sprint?): com.example.f1_calendar.model.domain.Sprint? {
        return if (sprint != null) com.example.f1_calendar.model.domain.Sprint(
            eventType = "Sprint",
            parseDateTime(sprint.date, sprint.time)
        ) else null

    }

    private fun mapQualifying(qualifying: Qualifying): com.example.f1_calendar.model.domain.Qualifying {
        return com.example.f1_calendar.model.domain.Qualifying(
            eventType = "Qualifying",
            parseDateTime(qualifying.date, qualifying.time)
        )
    }

    private fun mapThirdPractice(thirdPractice: com.example.f1_calendar.model.api.ThirdPractice?): ThirdPractice? {
        return if (thirdPractice != null) ThirdPractice(
            eventType = "Third Practice",
            parseDateTime(thirdPractice.date, thirdPractice.time)
        ) else null
    }

    private fun mapSecondPractice(secondPractice: SecondPractice): com.example.f1_calendar.model.domain.SecondPractice {
        return com.example.f1_calendar.model.domain.SecondPractice(
            eventType = "Second Practice",
            parseDateTime(secondPractice.date, secondPractice.time)
        )
    }

    private fun mapCircuit(circuit: Circuit): com.example.f1_calendar.model.domain.Circuit {
        return com.example.f1_calendar.model.domain.Circuit(
            location = mapCircuitLocation(circuit.Location),
            circuitName = circuit.circuitName
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

    private fun mapFirstPractice(firstPractice: com.example.f1_calendar.model.api.FirstPractice): FirstPractice {
        return FirstPractice(
            eventType = "First Practice",
            parseDateTime(firstPractice.date, firstPractice.time)
        )
    }

    private fun parseDateTime(date: String, time: String): ZonedDateTime {
        return ZonedDateTime.parse(
            "${date}T$time",
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)
        )
    }
}