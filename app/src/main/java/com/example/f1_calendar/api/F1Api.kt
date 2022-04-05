package com.example.f1_calendar.api

import com.example.f1_calendar.model.api.ApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface F1Api {
    @GET("current.json")
    fun getCurrentSeasonData(): Single<ApiResponse>
}