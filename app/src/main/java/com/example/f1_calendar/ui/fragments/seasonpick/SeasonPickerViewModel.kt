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

/**
 * TODO
 *  1. Move min max to this viewmodel's UI state (see DetailsViewModel for inspiration).
 *  2. Create interface SelectedSeasonProvider that has val season: LiveData<String>
 *  3. Refactor RaceListFragment to work with this interface instead of the whole SeasonPickerViewModel,
 *   because it doesn't need to know that it has anything other than the currently picked season.
 */