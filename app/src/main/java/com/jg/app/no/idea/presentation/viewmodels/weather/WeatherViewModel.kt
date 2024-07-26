package com.jg.app.no.idea.presentation.viewmodels.weather

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.my_weather_app.data.Resource
import com.example.my_weather_app.domain.usecases.weather.WeatherInteractor
import com.example.my_weather_app.presentation.weather.models.HistoryModel
import com.jg.app.no.idea.R
import com.jg.app.no.idea.constants.GeneralConstants.WEATHER_RAIN
import com.jg.app.no.idea.constants.TimeInTheDay
import com.jg.app.no.idea.data.storage.PreferencesManager
import com.jg.app.no.idea.data.weather.WeatherEntity
import com.jg.app.no.idea.presentation.base.BaseViewModel
import com.jg.app.no.idea.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utilities.TimeUtils
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val preferencesManager: PreferencesManager
) : BaseViewModel() {

    val addressState = MutableStateFlow("")

    val address = MutableLiveData<String>()
    val iconWeather = MutableLiveData<Int>()
    val tempStr = MutableLiveData<String>()
    val sunriseStr = MutableLiveData<String>()
    val sunsetStr = MutableLiveData<String>()
    val isFetchSuccessful = MediatorLiveData<Event<Boolean>>()
    val historyList = MutableLiveData<ArrayList<HistoryModel>>()
    private val tempList = ArrayList<HistoryModel>()

    val isCurrentSelected = MutableLiveData<Boolean>(true)
    val isHistorySelected = MutableLiveData<Boolean>(false)

    var weatherMain: String? = null
    var temperature: Double? = null
    var sunrise: Long? = null
    var sunset: Long? = null

    fun currentSelected() {
        isCurrentSelected.value = true
        isHistorySelected.value = false
    }

    fun historySelected() {
        isCurrentSelected.value = false
        isHistorySelected.value = true
    }

    fun fetchWeatherDetails(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            when (val result = weatherInteractor.getWeatherDetails(latitude, longitude)) {
                is Resource.Success -> {
                    setFieldsData(result.data)
                    isFetchSuccessful.postValue(Event(true))
                }
                is Resource.HttpError -> {
                    // TODO: Display Http error code
                    isFetchSuccessful.postValue(Event(false))
                }
                else -> {
                    isFetchSuccessful.postValue(Event(false))
                }
            }
        }
    }

    fun setAddress(pAddress: ArrayList<String>) {
        if (pAddress.size > 1) {
            val country = pAddress[0]
            val city = pAddress[1]
            address.value = "$city City, $country"
        }
    }

    private fun setFieldsData(data: WeatherEntity?) {
        if (data != null) {
            weatherMain = data.weather?.get(0)?.main
            temperature = data.main?.temp
            sunrise = data.sys?.sunrise
            sunset = data.sys?.sunset

            if (!weatherMain.isNullOrEmpty()) {
                var icon = R.drawable.ic_sun
                if (weatherMain == WEATHER_RAIN) {
                    icon = R.drawable.ic_rain
                } else {
                    if (TimeUtils.getTimeInDay() == TimeInTheDay.EVENING) {
                        icon = R.drawable.ic_moon
                    }
                }
                iconWeather.postValue(icon)
            }

            if (temperature != null) {
                tempStr.postValue("$temperature°C")
            }

            if (sunrise != null) {
                val convertedValue = TimeUtils.formatDateFromLong(sunrise ?: 0)
                sunriseStr.postValue("Sunrise: $convertedValue")
            }

            if (sunset != null) {
                val convertedValue = TimeUtils.formatDateFromLong(sunset ?: 0)
                sunsetStr.postValue("Sunset: $convertedValue")
            }
        }
    }

    fun loadHistoryList() {
        viewModelScope.launch {
            val value = preferencesManager.readeHistoryList() ?: listOf()
            tempList.addAll(value)
            tempList.sortByDescending {
                it.number
            }
            historyList.postValue(tempList)
        }
    }

    fun saveHistoryList() {
        viewModelScope.launch {
            val newItem = HistoryModel()
            newItem.temp = tempStr.value ?: ""
            newItem.address = address.value ?: ""
            newItem.dateTime = TimeUtils.getDateNowStr()
            newItem.number = tempList.size + 1
            tempList.add(newItem)
            preferencesManager.saveHistoryList(tempList.toList())
        }
    }
}