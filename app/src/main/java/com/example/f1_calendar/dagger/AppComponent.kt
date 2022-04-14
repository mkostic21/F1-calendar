package com.example.f1_calendar.dagger

import com.example.f1_calendar.ui.fragments.details.DetailsFragment
import com.example.f1_calendar.ui.fragments.racelist.RaceListFragment
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
    fun inject(detailsFragment: DetailsFragment)
}