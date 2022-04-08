package com.example.f1_calendar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.MainActivityUiState
import com.example.f1_calendar.model.ui.RaceWeekListItem
import com.example.f1_calendar.model.ui.MainActivityUiStateMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(
    private val repository: RaceTableRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<MainActivityUiState>()
    val uiState: LiveData<MainActivityUiState> get() = _uiState

    private var completeList: List<RaceWeekListItem> = listOf()
    private val headerIdToIsCollapsed: MutableMap<String, Boolean> = mutableMapOf()
    private var currentList: List<RaceWeekListItem> = listOf()

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUiState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun toggleCollapsedHeader(header: RaceWeekListItem.Header) {
        val editableList = currentList.toMutableList()

        val position = editableList.indexOf(header)
        val headerUniqueId = header.id

        if (headerIdToIsCollapsed[headerUniqueId] == true) {
            headerIdToIsCollapsed[headerUniqueId] = false

            val realIndex = completeList.indexOf(header)

            //restore header
            for (i in 1..4) {
                editableList.add(position + i, completeList[realIndex + i])
            }
            currentList = editableList

        } else {
            headerIdToIsCollapsed[headerUniqueId] = true

            //collapse header
            for (i in 1..4) {
                editableList.removeAt(position + 1)
            }
            currentList = editableList
        }

        _uiState.value = MainActivityUiState.Success(currentList)
    }

    private fun fetchUiState() {
        compositeDisposable.add(
            repository.getCurrentSeasonRaceTable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { raceTable ->
                    val raceWeekList = MainActivityUiStateMapper.mapRaceWeekList(raceTable = raceTable)

                    completeList = raceWeekList
                    currentList = completeList

                    MainActivityUiState.Success(raceWeekList)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _uiState.value = MainActivityUiState.Loading
                }
                .subscribeBy(
                    onSuccess = { uiStateSuccess ->
                        _uiState.value = uiStateSuccess
                    },
                    onError = { t ->
                        _uiState.value = MainActivityUiState.Error(t)
                    }
                )
        )
    }
}