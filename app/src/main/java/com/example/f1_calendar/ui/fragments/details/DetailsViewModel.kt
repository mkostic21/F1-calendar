package com.example.f1_calendar.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiState
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiStateMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: RaceTableRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<DetailsFragmentUiState>()
    val uiState: LiveData<DetailsFragmentUiState> get() = _uiState
    private val compositeDisposable = CompositeDisposable()

    fun fetchUiState(circuitId: String, season: String) {
        val disposable = repository.getCircuit(season = season, circuitId = circuitId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { circuit ->
                DetailsFragmentUiStateMapper.map(circuit = circuit)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _uiState.value = DetailsFragmentUiState.Loading
            }
            .subscribeBy(
                onSuccess = { uiStateSuccess ->
                    _uiState.value = uiStateSuccess
                },
                onError = { t ->
                    _uiState.value = DetailsFragmentUiState.Error(t)
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}