package com.jg.app.no.idea.injection.modules

import com.example.my_weather_app.domain.usecases.weather.WeatherInteractor
import com.jg.app.no.idea.domain.network.MwaServiceProvider
import com.jg.app.no.idea.domain.weather.GetWeatherDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [SupplementaryModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideWeatherInteractor(mwaServiceProvider: MwaServiceProvider): WeatherInteractor {
        return WeatherInteractor(
            GetWeatherDetails(mwaServiceProvider)
        )
    }
}