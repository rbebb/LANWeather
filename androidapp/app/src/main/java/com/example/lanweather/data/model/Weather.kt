package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather (
    val coverage: String?,
    val weather: String?,
    val intensity: String?,
    val visibility: Double?
)