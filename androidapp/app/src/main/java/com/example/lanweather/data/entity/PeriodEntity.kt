package com.example.lanweather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.lanweather.data.model.Period

@Entity(tableName = "Period")
data class PeriodEntity(
    @PrimaryKey @ColumnInfo(name = "startTime")
    val startTime: String?,
    @ColumnInfo(name = "number")
    val number: Int? = 0,
    @ColumnInfo(name = "name")
    val name: String? = "",
    @ColumnInfo(name = "endTime")
    val endTime: String? = "",
    @ColumnInfo(name = "isDaytime")
    val isDayTime: Boolean? = true,
    @ColumnInfo(name = "temperature")
    val temperature: Int? = 0,
    @ColumnInfo(name = "temperatureUnit")
    val temperatureUnit: Char? = 'a',
    @ColumnInfo(name = "temperatureTrend")
    val temperatureTrend: String? = "",
    @ColumnInfo(name = "windSpeed")
    val windSpeed: String? = "",
    @ColumnInfo(name = "windDirection")
    val windDirection: String? = "",
    @ColumnInfo(name = "icon")
    val icon: String? = "",
    @ColumnInfo(name = "shortForecast")
    val shortForecast: String? = "",
    @ColumnInfo(name = "detailedForecast")
    val detailedForecast: String? = ""
) {
    @Ignore
    constructor(period: Period) : this (
        startTime = period.startTime,
        number = period.number,
        name = period.name,
        endTime = period.endTime,
        isDayTime = period.isDayTime,
        temperature = period.temperature,
        temperatureUnit = period.temperatureUnit,
        temperatureTrend = period.temperatureTrend,
        windSpeed = period.windSpeed,
        windDirection = period.windDirection,
        icon = period.icon,
        shortForecast = period.shortForecast,
        detailedForecast = period.detailedForecast
    )

}