package com.example.f1_calendar.model.ui.race_list

sealed class RaceListFragmentUiState {

    data class Success(
        val listItems: List<RaceWeekListItem>,
        ) : RaceListFragmentUiState()

    data class Error(val t: Throwable) : RaceListFragmentUiState()

    object Loading : RaceListFragmentUiState()
}