package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Json (
    @Json(name = "sensor")
    val sensor: Sensor,
    @Json(name = "nws")
    val nws: Nws
)