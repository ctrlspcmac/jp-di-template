package com.example.my_weather_app.presentation.weather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryModel(
    @Json(name = "temp") var temp: String = "",
    @Json(name = "address") var address: String = "",
    @Json(name = "dateTime") var dateTime: String? = "",
    @Json(name = "number") var number: Int = 0
) {
}