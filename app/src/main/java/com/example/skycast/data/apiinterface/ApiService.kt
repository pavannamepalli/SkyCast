package com.example.skycast.data.apiinterface

import com.example.skycast.data.model.geoposition.GeoPositionData
import retrofit2.Response
import retrofit2.http.GET
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
}