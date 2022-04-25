package com.example.f1_calendar.dagger

import android.content.Context
import androidx.room.Room
import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.room.RaceConverter
import com.example.f1_calendar.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context, converter: RaceConverter): F1Database {
        return Room.databaseBuilder(
            context.applicationContext,
            F1Database::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addTypeConverter(converter)
            .build()
    }
}