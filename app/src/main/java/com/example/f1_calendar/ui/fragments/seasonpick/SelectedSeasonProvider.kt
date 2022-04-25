package com.example.f1_calendar.ui.fragments.seasonpick

import androidx.lifecycle.LiveData

interface SelectedSeasonProvider {
    val season: LiveData<String>
}