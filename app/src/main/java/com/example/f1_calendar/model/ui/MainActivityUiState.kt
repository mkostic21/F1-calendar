package com.example.f1_calendar.model.ui

sealed class MainActivityUiState {

    data class Success(
        val listItems: List<RaceWeekListItem>,
        ) : MainActivityUiState()

    data class Error(val t: Throwable) : MainActivityUiState()

    object Loading : MainActivityUiState()
}