package com.example.f1_calendar.model.ui.racelist

import com.example.f1_calendar.model.domain.RaceTable
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.*

object RaceListFragmentUiStateMapper {
    fun mapRaceWeekList(raceTable: RaceTable): MutableList<RaceWeekListItem> {
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
            if (race.firstPractice != null && race.firstPractice.dateTime.toLocalTime() != LocalTime.MIDNIGHT) {
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

    fun mapCurrentRaceWeekList(
        raceList: List<RaceWeekListItem>,
        season: String
    ): List<RaceWeekListItem> {
        val collapsedRaceList = mutableListOf<RaceWeekListItem>()

        if (season != "2022") {
            return raceList
        } else {
            val currentDate = ZonedDateTime.now()
            var foundNextRace = false

            for (raceEvent in raceList) {
                if (raceEvent is RaceWeekListItem.Header) {
                    collapsedRaceList.add(raceEvent)
                    if (!foundNextRace && raceEvent.dateTime.isAfter(currentDate)) {
                        val nextRaceId = raceList.indexOf(raceEvent)

                        //add next-race events under header
                        for (i in 1..4) {
                            collapsedRaceList.add(raceList[nextRaceId + i])
                        }
                        foundNextRace = true
                    }
                }
            }
        }
        return collapsedRaceList
    }

    fun mapCollapsedHeaderIds(
        currentList: List<RaceWeekListItem>,
        season: String
    ): MutableMap<String, Boolean> {
        val collapsedHeaderIds = mutableMapOf<String, Boolean>()
        if (season != "2022") {
            return collapsedHeaderIds
        } else {
            val currentDate = ZonedDateTime.now()
            var foundNextRace = false
            for (item in currentList) {
                if (item is RaceWeekListItem.Header) {
                    collapsedHeaderIds[item.id] = true
                    if (!foundNextRace && item.dateTime.isAfter(currentDate)){
                        collapsedHeaderIds[item.id] = false
                        foundNextRace = true
                    }
                }
            }
        }
        return collapsedHeaderIds
    }

    fun mapNextRaceId(raceList: List<RaceWeekListItem>, season: String) : Int {
        return if(season != "2022"){
            0
        } else {
            val currentDate = ZonedDateTime.now()
            var nextRaceIndex = 0
            for (item in raceList) {
                if(item is RaceWeekListItem.Header){
                    if(item.dateTime.isAfter(currentDate)){
                        nextRaceIndex = raceList.indexOf(item)
                        break
                    }
                }
            }
            nextRaceIndex
        }
    }
}