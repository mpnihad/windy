/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.screens.homescreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.weather_data.WeatherDataItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val homeRepository = HomeRepository()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: MutableLiveData<Boolean> get() = _isLoading

    private val _getWeatherData: MutableLiveData<WeatherDataItem> = MutableLiveData()
    val weatherData: MutableLiveData<WeatherDataItem> get() = _getWeatherData

    init {
        getWeatherData("Iceland")
    }

    fun getWeatherData(country: String) {

        viewModelScope.launch {
            _isLoading.postValue(true)

            homeRepository.getWeatherDataByLocation(country = country).collect {

                _getWeatherData.postValue(it)
                _isLoading.postValue(false)
            }
        }
    }
}
