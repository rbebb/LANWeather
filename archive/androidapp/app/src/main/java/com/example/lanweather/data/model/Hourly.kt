package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly (
    val periods: List<Period>
)