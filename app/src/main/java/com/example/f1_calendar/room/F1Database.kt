package com.example.f1_calendar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.f1_calendar.model.domain.RaceTable
import com.example.f1_calendar.util.Constants

@Database(entities = [RaceTable::class], version = 1, exportSchema = false)
@TypeConverters(RaceConverter::class)
abstract class F1Database : RoomDatabase(){
    abstract fun getRaceTableDao(): RaceTableDao

    companion object {

        @Volatile
        private var instance: F1Database? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                F1Database::class.java,
                Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance ?: buildDatabase(context = context).also { instance = it }
        }

    }

}