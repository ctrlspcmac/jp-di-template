package com.jg.app.no.idea.injection.modules

import com.jg.app.no.idea.constants.ApiConstants.CONNECT_TIMEOUT
import com.jg.app.no.idea.constants.ApiConstants.READ_TIMEOUT
import com.jg.app.no.idea.domain.network.MwaServiceProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [SupplementaryModule::class])
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(
            CONNECT_TIMEOUT.toLong(),
            TimeUnit.SECONDS
        )
            .readTimeout(
                READ_TIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .followRedirects(true)
            .followSslRedirects(true)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMwaServiceProvider(
        moshi: Moshi,
        client: OkHttpClient
    ): MwaServiceProvider {
        return MwaServiceProvider(client,moshi)
    }
}