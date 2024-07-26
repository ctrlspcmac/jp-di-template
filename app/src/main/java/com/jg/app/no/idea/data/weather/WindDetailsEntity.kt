package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindDetailsEntity(
    @Json(name="speed")var speed: Double? = null,
    @Json(name="deg")var deg: Long? = null,
    @Json(name="gust")var gust: Double? = null
) {
}