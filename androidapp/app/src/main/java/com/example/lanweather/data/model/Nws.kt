package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nws (
    @Json(name = "current")
    val current: Current,
    @Json(name = "hourly")
    val hourly: Hourly,
    @Json(name = "daily")
    val daily: Daily
)