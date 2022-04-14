package com.example.f1_calendar.ui.fragments.racelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiState
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiStateMapper
import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RaceListViewModel @Inject constructor(
    private val repository: RaceTableRepository
) : ViewModel() {
    private val _uiState = MutableLiveData<RaceListFragmentUiState>()
    val uiState: LiveData<RaceListFragmentUiState> get() = _uiState

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

        _uiState.value = RaceListFragmentUiState.Success(currentList)
    }

    private fun fetchUiState() {
        compositeDisposable.add(
            repository.getCurrentSeasonRaceTable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { raceTable ->
                    completeList = RaceListFragmentUiStateMapper.mapRaceWeekList(raceTable = raceTable)
                    currentList = completeList
                    RaceListFragmentUiState.Success(completeList)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _uiState.value = RaceListFragmentUiState.Loading
                }
                .subscribeBy(
                    onSuccess = { uiStateSuccess ->
                        _uiState.value = uiStateSuccess
                    },
                    onError = { t ->
                        _uiState.value = RaceListFragmentUiState.Error(t)
                    }
                )
        )
    }
}