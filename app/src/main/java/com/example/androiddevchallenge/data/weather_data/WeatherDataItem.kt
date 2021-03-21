package com.example.androiddevchallenge.data.weather_data

data class WeatherDataItem(
    val country: String,
    val currentTemp: Int,
    val currentWeather: String,
    val date: String,
    val locality: String,
    val nearByCity: List<NearByCity>,
    val tempByTime: List<TempByTime>,
    val tempInDetail: TempInDetail
)