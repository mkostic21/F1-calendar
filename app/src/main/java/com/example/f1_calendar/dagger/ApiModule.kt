package com.example.f1_calendar.dagger

import android.content.Context
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.room.F1Database
import com.example.f1_calendar.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideRepository(api: F1Api, db: F1Database): RaceTableRepository {
        return F1ApiRaceTableRepository(api, db)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): F1Database {
        return F1Database.invoke(context = context)
    }

}