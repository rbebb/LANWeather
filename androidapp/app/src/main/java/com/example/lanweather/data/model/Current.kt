package com.example.lanweather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Current (
    @Json(name = "temperature")
    val temperature: Double?,
    @Json(name = "maxTemperature")
    val maxTemperature: Double?,
    @Json(name = "minTemperature")
    val minTemperature: Double?,
    @Json(name = "relativeHumidity")
    val relativeHumidity: Int?,
    @Json(name = "windChill")
    val windChill: Double?,
    @Json(name = "windDirection")
    val windDirection: Double?,
    @Json(name = "windSpeed")
    val windSpeed: Double,
    @Json(name = "windGust")
    val windGust: Double?,
    @Json(name = "weather")
    val weather: Weather?,
    @Json(name = "probabilityOfPrecipitation")
    val probabilityOfPrecipitation: Int?
)