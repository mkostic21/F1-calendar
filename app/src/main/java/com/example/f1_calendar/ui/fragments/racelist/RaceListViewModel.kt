package com.example.f1_calendar.ui.fragments.racelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiState
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiStateMapper
import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem
import com.example.f1_calendar.util.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class RaceListViewModel @Inject constructor(
    private val repository: RaceTableRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<RaceListFragmentUiState>()
    val uiState: LiveData<RaceListFragmentUiState> get() = _uiState

    private var currentSeason: String = ""
    private var completeList: List<RaceWeekListItem> = listOf()
    private var headerIdToIsCollapsed: MutableMap<String, Boolean> = mutableMapOf()
    private var currentList: List<RaceWeekListItem> = listOf()
    private var nextRaceId = 0

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUiState(season = ZonedDateTime.now().year.toString())
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

        _uiState.value = RaceListFragmentUiState.Success(listItems = currentList, nextRaceId = nextRaceId)
    }

    fun fetchUiState(season: String) {
        if (season == currentSeason) {
            return
        } else {
            val disposable = repository.getRaceTable(season = season)
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.computation)
                .map { raceTable ->
                    completeList =
                        RaceListFragmentUiStateMapper.mapRaceWeekList(raceTable = raceTable)
                    currentList = RaceListFragmentUiStateMapper.mapCurrentRaceWeekList(
                        raceList = completeList,
                        season = currentSeason
                    )
                    headerIdToIsCollapsed = RaceListFragmentUiStateMapper.mapCollapsedHeaderIds(
                        currentList = currentList,
                        season = currentSeason
                    )
                    RaceListFragmentUiState.Success(
                        listItems = currentList,
                        nextRaceId = RaceListFragmentUiStateMapper.mapNextRaceId(currentList, currentSeason)
                    )
                }
                .observeOn(schedulerProvider.main)
                .doOnSubscribe {
                    _uiState.value = RaceListFragmentUiState.Loading
                }
                .subscribeBy(
                    onSuccess = { uiStateSuccess ->
                        nextRaceId = uiStateSuccess.nextRaceId
                        _uiState.value = uiStateSuccess
                    },
                    onError = { t ->
                        _uiState.value = RaceListFragmentUiState.Error(t)
                    }
                )
            compositeDisposable.add(disposable)

            currentSeason = season
        }
    }
}