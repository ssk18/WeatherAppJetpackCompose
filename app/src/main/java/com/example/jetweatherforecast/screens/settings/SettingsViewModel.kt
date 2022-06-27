package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository): ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getUnits().distinctUntilChanged()
                .collect {
                    listOfUits -> if(listOfUits.isNullOrEmpty()){
                    Log.d("TAG", "Empty units")
                }else {
                    _unitList.value = listOfUits
                }
                }
        }
    }
    fun insertUnit(unit: Unit) =
        viewModelScope.launch { weatherDbRepository.insertUnit(unit) }
    fun deleteUnit(unit: Unit) =
        viewModelScope.launch { weatherDbRepository.deleteUnit(unit) }
    fun deleteAllUnits() =
        viewModelScope.launch { weatherDbRepository.deleteAllUnits() }
}