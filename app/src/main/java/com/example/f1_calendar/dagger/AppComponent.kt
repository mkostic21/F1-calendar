package com.example.f1_calendar.dagger

import com.example.f1_calendar.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        AppModule::class,
        ApiModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}