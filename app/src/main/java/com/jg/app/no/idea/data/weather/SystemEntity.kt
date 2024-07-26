package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SystemEntity(
    @Json(name="type")var type: Int? = null,
    @Json(name="id")var id: Long? = null,
    @Json(name="country")var country: String? = null,
    @Json(name="sunrise")var sunrise: Long? = null,
    @Json(name="sunset")var sunset: Long? = null
) {
}
