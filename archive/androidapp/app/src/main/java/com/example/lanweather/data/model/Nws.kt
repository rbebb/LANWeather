package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nws (
    val current: Current,
    val hourly: Hourly,
    val daily: Daily
)