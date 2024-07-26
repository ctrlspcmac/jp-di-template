package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherItemEntity(
    @Json(name="id")var id: Long? = null,
    @Json(name="main")var main: String? = null,
    @Json(name="description")var description: String? = null,
    @Json(name="icon")var icon: String? = null
) {
}
