package com.example.f1_calendar.ui

import com.example.f1_calendar.model.ui.RaceWeekListItem

interface OnItemSelectedListener {
    fun onHeaderItemSelected(header: RaceWeekListItem.Header)
}