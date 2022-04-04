package com.example.f1_calendar.api

import com.example.f1_calendar.model.ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface F1Api {

    @GET("current.json")
    fun getData(): Observable<ApiResponse>
}