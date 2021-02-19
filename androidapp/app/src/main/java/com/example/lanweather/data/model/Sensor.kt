package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sensor (
    val temperature: Double = 0.0,
    val humidity: Double?
)