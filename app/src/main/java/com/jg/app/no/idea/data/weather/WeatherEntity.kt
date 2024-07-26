package com.jg.app.no.idea.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherEntity(
    @Json(name = "coord") var coord: CoordinateEntity? = null,
    @Json(name = "weather") var weather: List<WeatherItemEntity>? = null,
    @Json(name = "base") var base: String? = null,
    @Json(name = "main") var main: MainItemEntity? = null,
    @Json(name = "visibility") var visibility: Long? = null,
    @Json(name = "wind") var wind: WindDetailsEntity? = null,
    @Json(name = "clouds") var clouds: CloudsDetailsEntity? = null,
    @Json(name = "dt") var dt: Long? = null,
    @Json(name = "sys") var sys: SystemEntity? = null,
    @Json(name = "timezone") var timezone: Long? = null,
    @Json(name = "id") var id: Long? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "cod") var cod: Int? = null
) {
}
