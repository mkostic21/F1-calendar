package com.example.f1_calendar.api

import com.example.f1_calendar.model.api.ApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface F1Api {
    @GET("{season}.json")
    fun getSeasonData(@Path("season") season: String): Single<ApiResponse>

}