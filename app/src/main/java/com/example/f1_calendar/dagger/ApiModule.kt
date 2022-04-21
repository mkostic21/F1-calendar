package com.example.f1_calendar.dagger

import android.content.Context
import androidx.room.Room
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.room.RaceConverter
import com.example.f1_calendar.util.Constants
import com.example.f1_calendar.util.ZonedDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): F1Api {
        return retrofit.create(F1Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideRepository(api: F1Api, db: F1Database): RaceTableRepository {
        return F1ApiRaceTableRepository(api, db)
    }

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

    @Provides
    fun provideConverter(gson: Gson): RaceConverter{
        return RaceConverter(gson)
    }

    @Singleton
    @Provides
    fun provideGsonInstance(): Gson {
        return GsonBuilder().registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter()).create()
    }

}