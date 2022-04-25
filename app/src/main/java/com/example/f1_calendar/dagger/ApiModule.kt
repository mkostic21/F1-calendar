package com.example.f1_calendar.dagger

import com.example.f1_calendar.api.F1Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): F1Api {
        return retrofit.create(F1Api::class.java)
    }
}