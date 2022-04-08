package com.example.f1_calendar.ui.fragments.race_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.domain.RaceTableRepository

class RaceListViewModelProviderFactory(
    private val repository: RaceTableRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RaceListViewModel(repository) as T
    }
}