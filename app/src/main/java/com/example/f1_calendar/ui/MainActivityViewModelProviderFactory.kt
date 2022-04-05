package com.example.f1_calendar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.domain.RaceTableRepository

class MainActivityViewModelProviderFactory(
    private val repository: RaceTableRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}