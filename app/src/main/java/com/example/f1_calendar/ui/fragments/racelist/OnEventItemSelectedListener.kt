package com.example.f1_calendar.ui.fragments.racelist

import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem

interface OnEventItemSelectedListener {
    fun showDetails(event: RaceWeekListItem.Event)
}