package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainItemEntity(
    @Json(name="temp")var temp: Double? = null,
    @Json(name="feels_like")var feels_like: Double? = null,
    @Json(name="temp_min")var temp_min: Double? = null,
    @Json(name="temp_max")var temp_max: Double? = null,
    @Json(name="pressure")var pressure: Long? = null,
    @Json(name="humidity")var humidity: Long? = null,
    @Json(name="sea_level")var sea_level: Long? = null,
    @Json(name="grnd_level")var grnd_level: Long? = null
) {
}

