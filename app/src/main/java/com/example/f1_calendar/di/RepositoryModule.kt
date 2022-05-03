package com.example.f1_calendar.di

import android.content.Context
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.room.RaceTableDao
import com.example.f1_calendar.util.InternetConnectionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(api: F1Api, dao: RaceTableDao, handler: InternetConnectionHandler): RaceTableRepository {
        return F1ApiRaceTableRepository(f1Api = api, roomDatabase = dao, connectionHandler = handler)
    }

    @Provides
    fun provideInternetConnectionHandler(@ApplicationContext context: Context) : InternetConnectionHandler {
        return InternetConnectionHandler(context = context)
    }
}