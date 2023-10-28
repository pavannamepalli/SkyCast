package com.example.skycast.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycast.data.model.searchcities.LocationList
import com.example.skycast.data.repository.SearchRepository
import com.example.skycast.utils.NetworkUtils
import com.example.skycast.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel( private val repository: SearchRepository,  private val  context: Context) :ViewModel() {

    private val _locationListLiveData = MutableLiveData<Resource<LocationList>>()

    val locationListLiveData: LiveData<Resource<LocationList>>
        get() = _locationListLiveData
    fun getCitiesList(s: CharSequence?) {

        if (NetworkUtils.isNetworkAvailable(context = context)) {
            viewModelScope.launch {
                val result = repository.getLocationList(s)
                print("locationList + $result")
                _locationListLiveData.value = result
            }
        } else {
            _locationListLiveData.value = Resource.Error("No internet connection", null)
        }

    }
}