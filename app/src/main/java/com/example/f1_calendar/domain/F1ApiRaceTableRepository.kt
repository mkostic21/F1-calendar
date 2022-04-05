package com.example.f1_calendar.domain

import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.model.api.Circuit
import com.example.f1_calendar.model.api.Qualifying
import com.example.f1_calendar.model.api.SecondPractice
import com.example.f1_calendar.model.api.Sprint
import com.example.f1_calendar.model.domain.*
import io.reactivex.rxjava3.core.Single
import java.time.ZonedDateTime

class F1ApiRaceTableRepository(
    private val f1Api: F1Api,
) : RaceTableRepository {
    override fun getCurrentSeasonRaceTable(): Single<RaceTable> {
        return f1Api.getCurrentSeasonData().map { response ->
            RaceTable(
                races = mapRaces(response.MRData.raceTable),
                season = response.MRData.raceTable.season
            )
        }
    }

    private fun mapRaces(raceTable: com.example.f1_calendar.model.api.RaceTable): List<Race> {
        val domainRaces = mutableListOf<Race>()
        for (race in raceTable.Races)
            domainRaces.add(
                Race(
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


    private fun mapSprint(sprint: Sprint): com.example.f1_calendar.model.domain.Sprint {
        return com.example.f1_calendar.model.domain.Sprint(
            date = sprint.date,
            time = sprint.time
        )
    }

    private fun mapQualifying(qualifying: Qualifying): com.example.f1_calendar.model.domain.Qualifying {
        return com.example.f1_calendar.model.domain.Qualifying(
            date = qualifying.date,
            time = qualifying.time
        )
    }

    private fun mapThirdPractice(thirdPractice: com.example.f1_calendar.model.api.ThirdPractice): ThirdPractice {
        return ThirdPractice(
            date = thirdPractice.date,
            time = thirdPractice.time
        )
    }

    private fun mapSecondPractice(secondPractice: SecondPractice): com.example.f1_calendar.model.domain.SecondPractice {
        return com.example.f1_calendar.model.domain.SecondPractice(
            date = secondPractice.date,
            time = secondPractice.time
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
        return FirstPractice(date = firstPractice.date, time = firstPractice.time)
    }

    private fun parseDateTime(date: String, time: String): ZonedDateTime {
        return ZonedDateTime.parse(date+time) //todo: test
    }
}