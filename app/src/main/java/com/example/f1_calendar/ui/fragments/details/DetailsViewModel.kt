package com.example.f1_calendar.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiState
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiStateMapper
import com.example.f1_calendar.util.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: RaceTableRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<DetailsFragmentUiState>()
    val uiState: LiveData<DetailsFragmentUiState> get() = _uiState
    private val compositeDisposable = CompositeDisposable()

    fun fetchUiState(circuitId: String, season: String) {
        val disposable = repository.getCircuit(season = season, circuitId = circuitId)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.computation)
            .map { circuit ->
                DetailsFragmentUiStateMapper.map(circuit = circuit)
            }
            .observeOn(schedulerProvider.main)
            .doOnSubscribe {
                _uiState.value = DetailsFragmentUiState.Loading
            }
            .subscribeBy(
                onSuccess = { uiStateSuccess ->
                    _uiState.value = uiStateSuccess
                },
                onError = { t ->
                    _uiState.value = DetailsFragmentUiState.Error(t)
                },
                onComplete = {
                    _uiState.value = DetailsFragmentUiState.Empty
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}