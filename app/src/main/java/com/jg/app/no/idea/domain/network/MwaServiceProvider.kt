package com.jg.app.no.idea.domain.network

import com.jg.app.no.idea.constants.ApiConstants.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MwaServiceProvider (
    private val client: OkHttpClient,
    private val moshi: Moshi
) {

    private var restService: MwaService? = null

    fun getService(): MwaService? {
        // reset everytime
        restService = null

        if (restService == null) {
            restService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(MwaService::class.java)
        }
        return restService
    }
}