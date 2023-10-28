package com.example.skycast.data.model.currentcondition

data class CurrentTempItem(
    val CloudCover: Int,
    val DewPoint: DewPoint,
    val EpochTime: Int,
    val HasPrecipitation: Boolean,
    val IndoorRelativeHumidity: Int,
    val IsDayTime: Boolean,
    val Link: String,
    val LocalObservationDateTime: String,
    val MobileLink: String,
    val ObstructionsToVisibility: String,
    val PrecipitationType: Any,
    val RelativeHumidity: Int,
    val Temperature: Temperature,
    val UVIndex: Int,
    val UVIndexText: String,
    val Visibility: Visibility,
    val WeatherIcon: Int,
    val WeatherText: String,
    val Wind: Wind,
    val RealFeelTemperature : RealFeelTemperature,
    val Pressure:Pressure
)