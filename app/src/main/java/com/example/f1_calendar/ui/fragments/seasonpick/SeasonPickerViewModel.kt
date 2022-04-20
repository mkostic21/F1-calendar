package com.example.f1_calendar.ui.fragments.seasonpick

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeasonPickerViewModel : ViewModel() {
    private val _season = MutableLiveData("2022")
    val season: LiveData<String> get() = _season

    fun setSeason(value: String) {
        _season.value = value
    }
}