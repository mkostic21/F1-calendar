package com.example.f1_calendar.ui.fragments.racelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.domain.RaceTableRepository

class RaceListViewModelProviderFactory(
    private val repository: RaceTableRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RaceListViewModel(repository = repository) as T
    }
}