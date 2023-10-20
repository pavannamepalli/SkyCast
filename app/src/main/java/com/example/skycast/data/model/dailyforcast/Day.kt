package com.example.skycast.data.model.dailyforcast

data class Day(
    val HasPrecipitation: Boolean,
    val IceProbability: Int,
    val Icon: Int,
    val IconPhrase: String,
    val LongPhrase: String,
    val PrecipitationProbability: Int,
    val RainProbability: Int,
    val ShortPhrase: String,
    val SnowProbability: Int,
    val ThunderstormProbability: Int,
    val Wind: Wind
)