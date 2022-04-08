package com.example.f1_calendar.ui.fragments.race_list

import com.example.f1_calendar.model.ui.race_list.RaceWeekListItem

interface OnEventItemSelectedListener {
    fun onEventItemSelected(event: RaceWeekListItem)
}