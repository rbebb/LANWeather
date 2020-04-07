package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sensor (
    @Json(name = "temperature")
    val temperature: Double,
    @Json(name = "humidity")
    val humidity: Double?
)