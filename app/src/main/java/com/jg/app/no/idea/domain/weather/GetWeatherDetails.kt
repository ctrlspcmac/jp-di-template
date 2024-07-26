package com.jg.app.no.idea.domain.weather

import com.example.my_weather_app.data.Resource
import com.jg.app.no.idea.BuildConfig
import com.jg.app.no.idea.data.weather.WeatherEntity
import com.jg.app.no.idea.domain.network.MwaServiceProvider
import com.jg.app.no.idea.domain.network.SafeApiCall

class GetWeatherDetails(private val mwaServiceProvider: MwaServiceProvider) {

    suspend operator fun invoke(latitude: Double, longitude: Double): Resource<WeatherEntity> {
        val mwaService = mwaServiceProvider.getService()
            ?: return Resource.OtherError(Throwable("Mwa service not found"))
        val appKey = BuildConfig.WEATHER_KEY
        return when (val result =
            SafeApiCall { mwaService.getWeatherDetails(latitude, longitude, appKey, "metric") }) {
            is Resource.Success -> {
                if (result.data != null) {
                    Resource.Success(result.data)
                } else {
                    Resource.OtherError(Throwable("Null data"))
                }
            }
            is Resource.HttpError -> Resource.HttpError(result.code, result.throwable)
            is Resource.OtherError -> Resource.OtherError(result.throwable)
        }
    }
}