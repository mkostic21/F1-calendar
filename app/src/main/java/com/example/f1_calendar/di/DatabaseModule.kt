package com.example.f1_calendar.di

import android.content.Context
import androidx.room.Room
import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.room.RaceConverter
import com.example.f1_calendar.room.RaceTableDao
import com.example.f1_calendar.util.Constants
import com.example.f1_calendar.util.ZonedDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.ZonedDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, converter: RaceConverter): F1Database {
        return Room.databaseBuilder(
            context.applicationContext,
            F1Database::class.java,
            Constants.DATABASE_NAME
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