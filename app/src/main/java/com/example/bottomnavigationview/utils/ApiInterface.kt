package com.example.bottomnavigationview.utils

import com.example.bottomnavigationview.ui.weather.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("city")city: String,
        @Query("units")units: String,
        @Query("appId")apiKey: String
    ): Response<CurrentWeather>
}