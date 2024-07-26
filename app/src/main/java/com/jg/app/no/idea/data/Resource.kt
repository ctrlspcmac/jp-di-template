package com.example.my_weather_app.data

sealed class Resource<out T>(
    val data: T? = null,
    val code: Int? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class HttpError(code: Int? = null, throwable: Throwable? = null): Resource<Nothing>(null, code, throwable)
    class OtherError(throwable: Throwable? = null): Resource<Nothing>(null, null, throwable)
}