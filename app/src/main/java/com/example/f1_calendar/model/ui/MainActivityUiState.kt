package com.example.f1_calendar.model.ui

sealed class MainActivityUiState {


    data class Success(
        val test: String
        //todo: data for UI
    ) : MainActivityUiState()

    data class Error(val t: Throwable)

    object Loading : MainActivityUiState()
}