package com.example.f1_calendar.model.domain

import java.time.ZonedDateTime

data class RaceTable(
    val races: List<Race>,
    val season: String
)

data class Race(
    val circuit: Circuit,
    val firstPractice: FirstPractice,
    val qualifying: Qualifying,
    val secondPractice: SecondPractice,
    val sprint: Sprint,
    val thirdPractice: ThirdPractice,
    val dateTime: ZonedDateTime,
    val raceName: String,
    val round: String,
)

data class Circuit(
    val location: Location,
    val circuitName: String
)

data class Location(
    val country: String,
    val lat: String,
    val long: String,
    val locality: String
)

//todo: switch to ZonedDateTime
data class FirstPractice(
    val date: String,
    val time: String
)

data class SecondPractice(
    val date: String,
    val time: String
)

data class ThirdPractice(
    val date: String,
    val time: String
)

data class Qualifying(
    val date: String,
    val time: String
)

data class Sprint(
    val date: String,
    val time: String
)