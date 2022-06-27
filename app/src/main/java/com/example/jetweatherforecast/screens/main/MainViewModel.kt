package com.example.jetweatherforecast.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    val data: MutableState<DataOrException<WeatherObject, Boolean,Exception>>
    =  mutableStateOf(DataOrException(null, true, Exception("")))

    suspend fun getWeatherData(city: String, units: String): DataOrException<WeatherObject, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }
}