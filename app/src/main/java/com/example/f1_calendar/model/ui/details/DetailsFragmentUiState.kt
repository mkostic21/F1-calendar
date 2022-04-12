package com.example.f1_calendar.model.ui.details


sealed class DetailsFragmentUiState {

    data class Success(
        val lat: String,
        val long: String,
        val url: String
    ) : DetailsFragmentUiState()

    data class Error(val t: Throwable) : DetailsFragmentUiState()

    object Loading : DetailsFragmentUiState()
}