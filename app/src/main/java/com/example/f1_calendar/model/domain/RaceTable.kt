package com.example.f1_calendar.model.domain

import java.time.ZonedDateTime

data class RaceTable(
    val races: List<Race>,
    val season: String
)

data class Race(
    val eventType: String,
    val circuit: Circuit,
    val firstPractice: FirstPractice,
    val qualifying: Qualifying,
    val secondPractice: SecondPractice,
    val sprint: Sprint?,
    val thirdPractice: ThirdPractice?,
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

data class FirstPractice(
    val eventType: String,
    val dateTime: ZonedDateTime
)

data class SecondPractice(
    val eventType: String,
    val dateTime: ZonedDateTime
)

data class ThirdPractice(
    val eventType: String,
    val dateTime: ZonedDateTime?
)

data class Qualifying(
    val eventType: String,
    val dateTime: ZonedDateTime
)

data class Sprint(
    val eventType: String,
    val dateTime: ZonedDateTime?
)