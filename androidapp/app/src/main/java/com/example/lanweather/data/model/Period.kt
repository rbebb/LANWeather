package com.example.lanweather.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Period (
    @Json(name = "startTime")
    val startTime: String?,
    @Json(name = "number")
    val number: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "endTime")
    val endTime: String?,
    @Json(name = "isDayTime")
    val isDayTime: Boolean?,
    @Json(name = "temperature")
    val temperature: Int?,
    @Json(name = "temperatureUnit")
    val temperatureUnit: Char?,
    @Json(name = "temperatureTrend")
    val temperatureTrend: String?,
    @Json(name = "windSpeed")
    val windSpeed: String?,
    @Json(name = "windDirection")
    val windDirection: Char?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "shortForecast")
    val shortForecast: String?,
    @Json(name = "detailedForecast")
    val detailedForecast: String?
) : Parcelable