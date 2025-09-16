package com.example.skycast.data.repository

import android.content.Context
import com.example.skycast.data.apiinterface.ApiService
import com.example.skycast.data.model.searchcities.LocationList
import com.example.skycast.utils.Constants
import com.example.skycast.utils.Resource
import com.google.gson.Gson

class SearchRepository(private val apiService: ApiService, private val  context: Context) {

    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    suspend  fun getLocationList(s: CharSequence?): Resource<LocationList> {
        return try {
            val response = apiService.getLocationList(apiKey = Constants.API_KEY, query = s.toString(), language = Constants.LANGUAGE)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {

                    editor.putString("locationListData", Gson().toJson(data))
                    editor.commit()

                    Resource.Success(data)

                } else {

                    Resource.Error("Empty response", null)
                }
            } else {
                val cachedData = sharedPreferences.getString("locationListData", null)
                val cachedApiResponse = Gson().fromJson(cachedData, LocationList::class.java)
                Resource.Success(cachedApiResponse)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}", null)
        }

    }
}