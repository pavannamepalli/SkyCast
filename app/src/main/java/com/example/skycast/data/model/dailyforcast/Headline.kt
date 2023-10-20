package com.example.skycast.data.model.dailyforcast

data class Headline(
    val EffectiveDate: String,
    val EffectiveEpochDate: Int,
    val Severity: Int,
    val Text: String
)