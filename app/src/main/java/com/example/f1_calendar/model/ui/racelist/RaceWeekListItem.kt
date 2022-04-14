package com.example.f1_calendar.model.ui.racelist

import java.time.ZonedDateTime

sealed class RaceWeekListItem {

    data class Header(
        val raceName:String,
        val circuitName: String,
        val country: String,
        val dateTime: ZonedDateTime,
        val id: String
    ) : RaceWeekListItem() {
        override fun getDiffUtilId(): String {
            return "Header-$id"
        }
    }

    data class Event(
        val raceName: String,
        val eventType: String,
        val dateTime: ZonedDateTime,
        val circuitId: String
    ) : RaceWeekListItem(){
        override fun getDiffUtilId():String {
            return "Event-$dateTime"
        }
    }

    abstract fun getDiffUtilId():String
}