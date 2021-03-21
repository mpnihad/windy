package com.example.androiddevchallenge.screens.homescreen



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.weather_data.WeatherDataItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

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