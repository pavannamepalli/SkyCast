package com.example.skycast.data.model.geoposition

data class TimeZone(
    val Code: String,
    val GmtOffset: Double,
    val IsDaylightSaving: Boolean,
    val Name: String,
    val NextOffsetChange: Any
)