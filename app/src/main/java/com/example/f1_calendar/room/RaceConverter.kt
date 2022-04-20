package com.example.f1_calendar.room

import androidx.room.TypeConverter
import com.example.f1_calendar.model.domain.Race
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RaceConverter {

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)!!

    @TypeConverter
    fun fromRace(value: List<Race>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toRace(value: String): List<Race> {
        return Gson().fromJson(value)
    }
}