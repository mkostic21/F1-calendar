package com.example.f1_calendar.dagger

import com.example.f1_calendar.room.RaceConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class ConverterModule {

    @Provides
    fun provideConverter(gson: Gson): RaceConverter {
        return RaceConverter(gson)
    }
}