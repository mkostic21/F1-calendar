package com.example.f1_calendar.dagger

import android.content.Context
import androidx.room.Room
import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.room.RaceConverter
import com.example.f1_calendar.room.RaceTableDao
import com.example.f1_calendar.util.Constants.Companion.DATABASE_NAME
import com.example.f1_calendar.util.ZonedDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.time.ZonedDateTime
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context, converter: RaceConverter): F1Database {
        return Room.databaseBuilder(
            context.applicationContext,
            F1Database::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addTypeConverter(converter)
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabaseDao(db: F1Database): RaceTableDao {
        return db.getRaceTableDao()
    }

    @Provides
    fun provideConverter(gson: Gson): RaceConverter {
        return RaceConverter(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter()).create()
    }
}