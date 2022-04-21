package com.example.f1_calendar.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.f1_calendar.model.domain.Race
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RaceConverter (private val gson:Gson){

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)!!

    @TypeConverter
    fun fromRace(value: List<Race>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRace(value: String): List<Race> {
        return gson.fromJson(value)
    }
}