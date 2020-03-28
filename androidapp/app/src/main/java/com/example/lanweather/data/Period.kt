package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Periods")
data class Period(
    @PrimaryKey @ColumnInfo(name = "startTime")
    val startTime: String?,
    @ColumnInfo(name = "number")
    val number: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "endTime")
    val endTime: String?,
    @ColumnInfo(name = "isDaytime")
    val isDayTime: Boolean?,
    @ColumnInfo(name = "temperature")
    val temperature: Int,
    @ColumnInfo(name = "temperatureUnit")
    val temperatureUnit: Char?,
    @ColumnInfo(name = "temperatureTrend")
    val temperatureTrend: String?,
    @ColumnInfo(name = "windSpeed")
    val windSpeed: String?,
    @ColumnInfo(name = "windDirection")
    val windDirection: Char?,
    @ColumnInfo(name = "icon")
    val icon: String?,
    @ColumnInfo(name = "shortForecast")
    val shortForecast: String?,
    @ColumnInfo(name = "detailedForecast")
    val detailedForecast: String?
) {
}