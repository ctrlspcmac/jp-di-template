package com.jg.app.no.idea.domain.network

import com.jg.app.no.idea.constants.ApiConstants.GET_WEATHER_DETAILS
import com.jg.app.no.idea.data.weather.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MwaService {

    @GET(GET_WEATHER_DETAILS)
    suspend fun getWeatherDetails(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") app_key: String,
        @Query("units") unit: String
    ): WeatherEntity

}