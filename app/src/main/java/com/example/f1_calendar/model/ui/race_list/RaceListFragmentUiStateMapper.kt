package com.example.f1_calendar.model.ui.race_list

import com.example.f1_calendar.model.domain.RaceTable
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
                    id = UUID.randomUUID().toString()
                )
            )

            raceWeekList.add(
                RaceWeekListItem.Event(
                    raceName = race.raceName,
                    eventType = race.firstPractice.eventType,
                    dateTime = race.firstPractice.dateTime
                )
            )
            raceWeekList.add(
                RaceWeekListItem.Event(
                    raceName = race.raceName,
                    eventType = race.secondPractice.eventType,
                    dateTime = race.secondPractice.dateTime
                )
            )
            if (race.sprint == null) {
                raceWeekList.add(
                    RaceWeekListItem.Event(
                        raceName = race.raceName,
                        eventType = race.thirdPractice!!.eventType,
                        dateTime = race.thirdPractice.dateTime!!
                    )
                )
            } else {
                raceWeekList.add(
                    RaceWeekListItem.Event(
                        raceName = race.raceName,
                        eventType = race.sprint.eventType,
                        dateTime = race.sprint.dateTime!!
                    )
                )
            }
            raceWeekList.add(
                RaceWeekListItem.Event(
                    raceName = race.raceName,
                    eventType = race.qualifying.eventType,
                    dateTime = race.qualifying.dateTime
                )
            )
        }
        return raceWeekList
    }

}