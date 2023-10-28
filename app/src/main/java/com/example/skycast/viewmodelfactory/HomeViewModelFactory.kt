package com.example.skycast.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skycast.data.apiinterface.ApiService
import com.example.skycast.data.repository.HomeRepository
import com.example.skycast.data.retrofit.RetrofitClient
import com.example.skycast.ui.viewmodel.HomeViewModel

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
        val homeRepository = HomeRepository(apiService,context)
        return HomeViewModel(homeRepository,context)  as T
    }

}
