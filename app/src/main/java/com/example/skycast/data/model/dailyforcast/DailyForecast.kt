package com.example.skycast.data.model.dailyforcast

data class DailyForecast(
    val Date: String,
    val Day: Day,
    val EpochDate: Int,
    val HoursOfSun: Double,
    val Moon: Moon,
    val Night: Night,
    val Sun: Sun,
    val Temperature: Temperature
)