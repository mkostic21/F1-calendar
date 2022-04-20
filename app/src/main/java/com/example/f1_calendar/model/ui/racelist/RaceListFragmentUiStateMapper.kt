package com.example.f1_calendar.model.ui.racelist

import com.example.f1_calendar.model.domain.RaceTable
import java.time.LocalTime
import java.util.*

object RaceListFragmentUiStateMapper {
    fun mapRaceWeekList(raceTable: RaceTable): MutableList<RaceWeekListItem>{
        val raceWeekList = mutableListOf<RaceWeekListItem>()
        for (race in raceTable.races) {
            raceWeekList.add(
                RaceWeekListItem.Header(
                    raceName = race.raceName,
                    circuitName = race.circuit.circuitName,
                    country = race.circuit.location.country,
                    dateTime = race.dateTime,
                    circuitId = race.circuit.circuitId,
                    id = UUID.randomUUID().toString()
                )
            )
            //if practice is missing or if practice doesn't have time to show -> add only HEADER items
            if(race.firstPractice != null && race.firstPractice.dateTime.toLocalTime() != LocalTime.MIDNIGHT) {
                raceWeekList.add(
                    RaceWeekListItem.Event(
                        raceName = race.raceName,
                        eventType = race.firstPractice.eventType,
                        dateTime = race.firstPractice.dateTime,
                        circuitId = race.circuit.circuitId
                    )
                )
                raceWeekList.add(
                    RaceWeekListItem.Event(
                        raceName = race.raceName,
                        eventType = race.secondPractice!!.eventType,
                        dateTime = race.secondPractice.dateTime,
                        circuitId = race.circuit.circuitId
                    )
                )
                if (race.sprint == null) {
                    raceWeekList.add(
                        RaceWeekListItem.Event(
                            raceName = race.raceName,
                            eventType = race.thirdPractice!!.eventType,
                            dateTime = race.thirdPractice.dateTime,
                            circuitId = race.circuit.circuitId
                        )
                    )
                } else {
                    raceWeekList.add(
                        RaceWeekListItem.Event(
                            raceName = race.raceName,
                            eventType = race.sprint.eventType,
                            dateTime = race.sprint.dateTime,
                            circuitId = race.circuit.circuitId
                        )
                    )
                }
                raceWeekList.add(
                    RaceWeekListItem.Event(
                        raceName = race.raceName,
                        eventType = race.qualifying!!.eventType,
                        dateTime = race.qualifying.dateTime,
                        circuitId = race.circuit.circuitId
                    )
                )
            }
        }
        return raceWeekList
    }

}