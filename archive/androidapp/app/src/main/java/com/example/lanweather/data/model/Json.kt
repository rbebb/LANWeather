package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Json (
    val sensor: Sensor,
    val nws: Nws
)