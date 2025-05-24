package com.example.lanweather.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Current (
    val temperature: Double?,
    val maxTemperature: Double?,
    val minTemperature: Double?,
    val relativeHumidity: Int?,
    val windChill: Double?,
    val windDirection: Double?,
    val windSpeed: Double,
    val windGust: Double?,
    val weather: Weather?,
    val probabilityOfPrecipitation: Int?
)