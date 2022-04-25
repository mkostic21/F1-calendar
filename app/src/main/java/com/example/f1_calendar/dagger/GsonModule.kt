package com.example.f1_calendar.dagger

import com.example.f1_calendar.util.ZonedDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.time.ZonedDateTime
import javax.inject.Singleton

@Module
class GsonModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter()).create()
    }
}