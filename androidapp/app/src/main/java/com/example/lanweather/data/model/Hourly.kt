package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly (
    @Json(name = "period")
    val period: List<Period>
)