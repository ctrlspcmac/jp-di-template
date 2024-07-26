package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloudsDetailsEntity(
    @Json(name="all")var all: Long? = null
) {
}
