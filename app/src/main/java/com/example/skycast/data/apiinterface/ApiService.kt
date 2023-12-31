package com.example.skycast.data.apiinterface

import com.example.skycast.data.model.currentcondition.CurrentTemp
import com.example.skycast.data.model.dailyforcast.DailyForcast
import com.example.skycast.data.model.geoposition.GeoPositionData
import com.example.skycast.data.model.searchcities.LocationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocalLocationDetails(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String,
        @Query("details") details: Boolean,
        @Query("toplevel") topLevel: Boolean
    ): Response<GeoPositionData>

    @GET("currentconditions/v1/{locationKey}")
    suspend fun getCurrentTempCondition(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean,
        ): Response<CurrentTemp>

    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun getForcastData(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean): Response<DailyForcast>

    @GET("locations/v1/cities/autocomplete")
    suspend fun getLocationList(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String,
                                 ): Response<LocationList>
}