package com.example.f1_calendar.ui.fragments.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.domain.F1ApiRaceTableRepository

class DetailsViewModelProviderFactory(private val repository: F1ApiRaceTableRepository, private val circuitId: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository = repository, circuitId = circuitId) as T
    }

}