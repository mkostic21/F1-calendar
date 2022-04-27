package com.example.f1_calendar.dagger

import android.app.Application
import android.content.Context
import com.example.f1_calendar.util.DefaultSchedulerProvider
import com.example.f1_calendar.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Singleton
    @Provides
    fun provideContext(): Context = app

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return DefaultSchedulerProvider()
    }
}