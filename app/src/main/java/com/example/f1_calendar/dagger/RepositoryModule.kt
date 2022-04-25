package com.example.f1_calendar.dagger

import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.room.RaceTableDao
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(api: F1Api, dao: RaceTableDao): RaceTableRepository {
        return F1ApiRaceTableRepository(api, dao)
    }
}