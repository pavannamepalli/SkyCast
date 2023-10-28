package com.example.skycast.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skycast.data.apiinterface.ApiService
import com.example.skycast.data.repository.SearchRepository
import com.example.skycast.data.retrofit.RetrofitClient
import com.example.skycast.ui.viewmodel.SearchViewModel

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
        val repository = SearchRepository(apiService,context)
        return SearchViewModel(repository,context) as T
    }
}