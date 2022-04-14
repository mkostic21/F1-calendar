package com.example.f1_calendar.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.domain.Location
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

    fun fetchUiState(circuitId: String) {
        val disposable = repository.getCircuit(circuitId = circuitId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { circuit ->
                DetailsFragmentUiState.Success(
                    lat = DetailsFragmentUiStateMapper.mapLat(circuit = circuit),
                    long = DetailsFragmentUiStateMapper.mapLong(circuit = circuit),
                    url = DetailsFragmentUiStateMapper.mapUrl(circuit = circuit)
                )
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

    fun getLocation(): Location {
        val state = _uiState.value as DetailsFragmentUiState.Success
        return Location(
            country = "",
            lat = state.lat,
            long = state.long,
            locality = ""
        )
    }

    fun getUrl(): String {
        val state = _uiState.value as DetailsFragmentUiState.Success
        return state.url
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}