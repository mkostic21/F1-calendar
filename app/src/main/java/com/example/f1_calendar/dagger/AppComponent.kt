package com.example.f1_calendar.dagger

import com.example.f1_calendar.ui.fragments.race_list.RaceListFragment
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

    fun inject(raceListFragment: RaceListFragment)
}