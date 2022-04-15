package com.example.f1_calendar.ui.fragments.racelist

import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem

interface OnHeaderItemSelectedListener {
    fun toggleHeader(header: RaceWeekListItem.Header)
    fun showDetails(header: RaceWeekListItem.Header)
}