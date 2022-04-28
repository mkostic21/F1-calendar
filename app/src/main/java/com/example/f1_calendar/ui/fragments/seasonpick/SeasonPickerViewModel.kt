package com.example.f1_calendar.ui.fragments.seasonpick

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1_calendar.model.ui.seasonpick.SeasonPickFragmentUiState
import com.example.f1_calendar.util.Constants.Companion.NUMBER_PICKER_MAX_VALUE
import com.example.f1_calendar.util.Constants.Companion.NUMBER_PICKER_MIN_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonPickerViewModel @Inject constructor(): ViewModel(), SelectedSeasonProvider {
    private val _season = MutableLiveData("2022")
    override val season: LiveData<String> get() = _season

    private val _uiState = MutableLiveData<SeasonPickFragmentUiState>()
    val uiState: LiveData<SeasonPickFragmentUiState> get() = _uiState

    init {
        fetchUiState()
    }

    fun fetchUiState(){
        _uiState.value = SeasonPickFragmentUiState(
            minValue = NUMBER_PICKER_MIN_VALUE,
            maxValue = NUMBER_PICKER_MAX_VALUE,
            value = _season.value!!.toInt()
        )
    }

    fun setSeason(value: String) {
        _season.value = value
        Log.d("response", "setSeason: $value")
    }
}