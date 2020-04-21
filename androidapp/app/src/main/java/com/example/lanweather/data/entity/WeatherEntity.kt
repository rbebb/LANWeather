package com.example.lanweather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.example.lanweather.data.model.Weather

@Entity(tableName = "Weather")
data class WeatherEntity (
    @ColumnInfo(name = "coverage")
    val coverage: String? = "",
    @ColumnInfo(name = "weather")
    val weather: String? = "",
    @ColumnInfo(name = "intensity")
    val intensity: String? = "",
    @ColumnInfo(name = "visibility")
    val visibility: Double?= 0.0
){
    @Ignore
    constructor(weather: Weather) : this (
        coverage = weather.coverage,
        weather = weather.weather,
        intensity = weather.intensity,
        visibility = weather.visibility
    )
}