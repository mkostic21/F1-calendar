package com.example.f1_calendar.model.ui

import java.time.ZonedDateTime

sealed class RaceWeekListItem {

    data class Header(
        val raceName:String,
        val circuitName: String,
        val country: String,
        val dateTime: ZonedDateTime
    ) : RaceWeekListItem() {
        override fun getDiffUtilId(): String {
            return "Header"
        }
    }

    data class Event(
        val raceName: String,
        val eventType: String, //first practice, second practice, third practice/sprint, qualifying
        val dateTime: ZonedDateTime
    ) : RaceWeekListItem(){
        override fun getDiffUtilId():String {
            return "Event"
        }
    }

    abstract fun getDiffUtilId():String
}