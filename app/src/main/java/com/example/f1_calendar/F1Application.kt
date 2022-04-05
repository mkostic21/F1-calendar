package com.example.f1_calendar

import android.app.Application
import com.example.f1_calendar.dagger.AppComponent
import com.example.f1_calendar.dagger.AppModule
import com.example.f1_calendar.dagger.DaggerAppComponent

class F1Application : Application() {
    lateinit var f1Component: AppComponent

    override fun onCreate() {
        super.onCreate()
        f1Component = initDagger(this)
    }

    private fun initDagger(app: F1Application): AppComponent =
        DaggerAppComponent.builder().appModule(AppModule(app)).build()
}