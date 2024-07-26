package com.jg.app.no.idea.domain.network

import com.example.my_weather_app.data.Resource
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object SafeApiCall {

    suspend operator fun <T> invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                val result = apiCall.invoke()
                if (result != null) {
                    Resource.Success(result as T)
                } else {
                    Resource.OtherError(Throwable("Parsing response error."))
                }

            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.OtherError(throwable)
                    is HttpException -> {
                        val code = throwable.code()
                        Resource.HttpError(code, throwable)
                    }
                    is JsonDataException -> Resource.OtherError(Throwable("Failed to parse the JSON response."))
                    else -> {
                        Resource.OtherError(throwable)
                    }
                }
            }
        }
    }

}