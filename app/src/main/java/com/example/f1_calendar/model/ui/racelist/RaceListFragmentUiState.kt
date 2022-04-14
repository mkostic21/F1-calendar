package com.example.f1_calendar.model.ui.racelist

sealed class RaceListFragmentUiState {

    data class Success(
        val listItems: List<RaceWeekListItem>,
        ) : RaceListFragmentUiState()

    data class Error(val t: Throwable) : RaceListFragmentUiState()

    object Loading : RaceListFragmentUiState()
}