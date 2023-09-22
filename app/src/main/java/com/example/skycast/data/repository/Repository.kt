package com.example.skycast.data.repository

import com.example.skycast.data.apiinterface.ApiService
import com.example.skycast.data.model.geoposition.GeoPositionData
import com.example.skycast.utils.Constants
import com.example.skycast.utils.Resource

class Repository(private val apiService: ApiService) {

    suspend fun getLocalLocationDetails(latitude: String, longitude: String): Resource<GeoPositionData> {
        return try {
            val response = apiService.getLocalLocationDetails(apiKey = Constants.ApiKey, query = "$latitude,$longitude", language = "en-us", details = false, topLevel = true)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("Empty response", null)
                }
            } else {
                Resource.Error("Error loading posts", null)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}", null)
        }
    }
}
