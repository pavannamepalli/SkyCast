package com.example.skycast.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycast.data.model.geoposition.GeoPositionData

import com.example.skycast.data.repository.Repository
import com.example.skycast.utils.NetworkUtils
import com.example.skycast.utils.Resource
import kotlinx.coroutines.launch


class GetLocationViewModel(
    private val repository: Repository,
    private val context: Context

) : ViewModel() {

    private val _locationLiveData = MutableLiveData<Resource<GeoPositionData>>()
    val locationLiveData: LiveData<Resource<GeoPositionData>>
        get() = _locationLiveData

    fun getLocalLocationDetails(latitude: String, longitude: String) {
        if (NetworkUtils.isNetworkAvailable(context = context)) {
            viewModelScope.launch {
                val result = repository.getLocalLocationDetails(latitude,longitude)
                _locationLiveData.value = result
            }
        } else {
            _locationLiveData.value = Resource.Error("No internet connection", null)
        }
    }
}