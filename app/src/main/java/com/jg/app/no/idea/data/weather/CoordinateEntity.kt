package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinateEntity(
    @Json(name="lon")var lon: Double? = null,
    @Json(name="lat")var lat: Double? = null
) {
}
