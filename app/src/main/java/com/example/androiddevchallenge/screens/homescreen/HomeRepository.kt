package com.example.androiddevchallenge.screens.homescreen

import com.example.androiddevchallenge.data.weather_data.DataWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository {
    fun getWeatherDataByLocation(country: String) = flow {

       delay(2000)
        emit(DataWeather().getWeatherDataObject().filter { it.country == country}[0])
    }.flowOn(Dispatchers.IO)
}