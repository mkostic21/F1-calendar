package com.example.f1_calendar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.MainActivityUiState
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivityViewModel(
    private val repository: RaceTableRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<MainActivityUiState>()
    val uiState: LiveData<MainActivityUiState> get() = _uiState

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUiState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun fetchUiState() {
        /* TODO:
        1. Fetch data from the repository
        2. Map it to MainActivityUiState
        3. Set _uiState's value to the Ui state (onSuccess)
         */
    }
}