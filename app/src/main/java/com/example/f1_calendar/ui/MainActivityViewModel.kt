package com.example.f1_calendar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.domain.RaceTableRepository
import com.example.f1_calendar.model.ui.MainActivityUiState
import com.example.f1_calendar.model.ui.RaceWeekListItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

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
        compositeDisposable.add(
            repository.getCurrentSeasonRaceTable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { raceTable ->
                    val raceWeekList = mutableListOf<RaceWeekListItem>()
                    for (race in raceTable.races) {
                        raceWeekList.add(
                            RaceWeekListItem.Header(
                                raceName = race.raceName,
                                circuitName = race.circuit.circuitName,
                                country = race.circuit.location.country,
                                dateTime = race.dateTime
                            )
                        )

                        //weekend events:
                        raceWeekList.add(
                            RaceWeekListItem.Event(
                                raceName = race.raceName,
                                eventType = race.firstPractice.eventType,
                                dateTime = race.firstPractice.dateTime
                            )
                        )
                        raceWeekList.add(
                            RaceWeekListItem.Event(
                                raceName = race.raceName,
                                eventType = race.secondPractice.eventType,
                                dateTime = race.secondPractice.dateTime
                            )
                        )
                        if (race.sprint == null) {
                            raceWeekList.add(
                                RaceWeekListItem.Event(
                                    raceName = race.raceName,
                                    eventType = race.thirdPractice!!.eventType,
                                    dateTime = race.thirdPractice.dateTime!!
                                )
                            )
                        } else {
                            raceWeekList.add(
                                RaceWeekListItem.Event(
                                    raceName = race.raceName,
                                    eventType = race.sprint.eventType,
                                    dateTime = race.sprint.dateTime!!
                                )
                            )
                        }
                        raceWeekList.add(RaceWeekListItem.Event(
                            raceName = race.raceName,
                            eventType = race.qualifying.eventType,
                            dateTime = race.qualifying.dateTime
                        ))
                    }
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