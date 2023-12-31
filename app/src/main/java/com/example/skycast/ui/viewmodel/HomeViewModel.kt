package com.example.skycast.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycast.data.model.currentcondition.CurrentTemp
import com.example.skycast.data.model.dailyforcast.DailyForcast
import com.example.skycast.data.model.geoposition.GeoPositionData

import com.example.skycast.data.repository.HomeRepository
import com.example.skycast.utils.NetworkUtils
import com.example.skycast.utils.Resource
import kotlinx.coroutines.launch


class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val context: Context

) : ViewModel() {

    private val _locationLiveData = MutableLiveData<Resource<GeoPositionData>>()
    private val _getCurrentTempLiveData = MutableLiveData<Resource<CurrentTemp>>()
    private val _getForecastDetailsLiveData = MutableLiveData<Resource<DailyForcast>>()

    val locationLiveData: LiveData<Resource<GeoPositionData>>
        get() = _locationLiveData
    val getCurrentTempLiveData: LiveData<Resource<CurrentTemp>>
        get() = _getCurrentTempLiveData

    val getForecastDetailsLiveData : LiveData<Resource<DailyForcast>>
        get() = _getForecastDetailsLiveData

    fun getLocalLocationDetails(latitude: String, longitude: String) {
        if (NetworkUtils.isNetworkAvailable(context = context)) {
            viewModelScope.launch {
                val result = homeRepository.getLocalLocationDetails(latitude,longitude)
                _locationLiveData.value = result
            }
        } else {
            _locationLiveData.value = Resource.Error("No internet connection", null)
        }
    }

    fun getCurrentWeather(key: String) {
        if (NetworkUtils.isNetworkAvailable(context = context)) {
            viewModelScope.launch {
                val result = homeRepository.getCurrentTempCondition(key)
                _getCurrentTempLiveData.value = result
            }
        } else {
            _getCurrentTempLiveData.value = Resource.Error("No internet connection", null)
        }

    }

    fun getForcastData(key: String){
        if (NetworkUtils.isNetworkAvailable(context = context)) {
            viewModelScope.launch {
                val result = homeRepository.getForcastData(key)
                _getForecastDetailsLiveData.value = result
            }
        } else {
            _getForecastDetailsLiveData.value = Resource.Error("No internet connection", null)
        }

    }
}