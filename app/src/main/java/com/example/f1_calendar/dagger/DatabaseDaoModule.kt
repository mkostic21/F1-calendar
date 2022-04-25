package com.example.f1_calendar.dagger

import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.room.RaceTableDao
import com.example.f1_calendar.room.RaceTableDao_Impl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseDaoModule {

    @Singleton
    @Provides
    fun provideDatabaseDao(db: F1Database): RaceTableDao {
        return RaceTableDao_Impl(db)
    }
}