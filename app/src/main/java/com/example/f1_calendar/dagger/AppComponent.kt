package com.example.f1_calendar.dagger

import com.example.f1_calendar.dagger.viewmodel.ViewModelModule
import com.example.f1_calendar.ui.fragments.details.DetailsFragment
import com.example.f1_calendar.ui.fragments.racelist.RaceListFragment
import com.example.f1_calendar.ui.fragments.seasonpick.SeasonPickFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        AppModule::class,
        ApiModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        DatabaseDaoModule::class,
        ConverterModule::class,
        GsonModule::class,
        RepositoryModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {
    fun inject(raceListFragment: RaceListFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(seasonPickFragment: SeasonPickFragment)
}