package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sensor (
    val temperature: Double,
    val humidity: Double?
)