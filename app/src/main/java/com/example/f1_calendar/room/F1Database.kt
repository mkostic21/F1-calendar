package com.example.f1_calendar.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.f1_calendar.model.domain.RaceTable

@Database(entities = [RaceTable::class], version = 1, exportSchema = false)
@TypeConverters(RaceConverter::class)
abstract class F1Database : RoomDatabase(){
    abstract fun getRaceTableDao(): RaceTableDao

}