package com.example.lanweather.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Period (
    val startTime: String?,
    val number: Int?,
    val name: String?,
    val endTime: String?,
    val isDayTime: Boolean?,
    val temperature: Int?,
    val temperatureUnit: Char?,
    val temperatureTrend: String?,
    val windSpeed: String?,
    val windDirection: String?,
    val icon: String?,
    val shortForecast: String?,
    val detailedForecast: String?
) : Parcelable