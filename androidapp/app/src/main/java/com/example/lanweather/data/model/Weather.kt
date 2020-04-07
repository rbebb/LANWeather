package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather (
    @Json(name = "coverage")
    val coverage: String?,
    @Json(name = "weather")
    val weather: String?,
    @Json(name = "intensity")
    val intensity: String?,
    @Json(name = "visibility")
    val visibility: Double?
)