package com.example.f1_calendar.util

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    val io: Scheduler
    val main: Scheduler
    val computation: Scheduler
}