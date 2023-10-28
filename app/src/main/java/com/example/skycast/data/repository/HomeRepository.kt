package com.example.skycast.data.repository

import android.content.Context
import com.example.skycast.data.apiinterface.ApiService
import com.example.skycast.data.model.currentcondition.CurrentTemp
import com.example.skycast.data.model.dailyforcast.DailyForcast
import com.example.skycast.data.model.geoposition.GeoPositionData
import com.example.skycast.utils.Constants
import com.example.skycast.utils.Resource
import com.google.gson.Gson


class HomeRepository(private val apiService: ApiService, private val  context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    suspend fun getLocalLocationDetails(latitude: String, longitude: String): Resource<GeoPositionData> {
        return try {
            val response = apiService.getLocalLocationDetails(apiKey = Constants.ApiKey, query = "$latitude,$longitude", language = "en-us", details = false, topLevel = true)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {

                    editor.putString("locationData", Gson().toJson(data))
                    editor.commit()

                    Resource.Success(data)

                } else {
                    Resource.Error("Empty response", null)
                }
            } else {
                val cachedData = sharedPreferences.getString("locationData", null)
                val cachedApiResponse = Gson().fromJson(cachedData, GeoPositionData::class.java)
                Resource.Success(cachedApiResponse)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}", null)
        }
    }

    suspend fun getCurrentTempCondition(key: String): Resource<CurrentTemp> {
        return try {
            val response = apiService.getCurrentTempCondition(apiKey = Constants.ApiKey, locationKey = key, language = "en-us", details = true)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    editor.putString("currentTemperatureData", Gson().toJson(data))
                    editor.commit()
                    Resource.Success(data)
                } else {
                    Resource.Error("Empty response", null)
                }
            } else {
                val cachedData = sharedPreferences.getString("currentTemperatureData", null)
                val cachedApiResponse = Gson().fromJson(cachedData, CurrentTemp::class.java)
                Resource.Success(cachedApiResponse)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}", null)
        }
    }

   suspend fun getForcastData(key: String): Resource<DailyForcast> {
        return try {
            val response = apiService.getForcastData(apiKey = Constants.ApiKey, locationKey = key, language = "en-us", details = true,metric = false)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    editor.putString("forecastData", Gson().toJson(data))
                    editor.commit()
                    Resource.Success(data)
                } else {
                    Resource.Error("Empty response", null)
                }
            } else {
                val cachedData = sharedPreferences.getString("forecastData", null)
                val cachedApiResponse = Gson().fromJson(cachedData, DailyForcast::class.java)
                Resource.Success(cachedApiResponse)
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}", null)
        }
    }
}
