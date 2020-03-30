package com.example.lanweather.data.entity

import androidx.room.*
import com.example.lanweather.data.model.Current

@Entity(tableName = "Current")
data class CurrentEntity (
    @ColumnInfo(name = "temperature")
    val temperature: Double?,
    @ColumnInfo(name = "maxTemperature")
    val maxTemperature: Double?,
    @ColumnInfo(name = "minTemperature")
    val minTemperature: Double?,
    @ColumnInfo(name = "relativeHumidity")
    val relativeHumidity: Int?,
    @ColumnInfo(name = "windChill")
    val windChill: Double?,
    @ColumnInfo(name = "windDirection")
    val windDirection: Double?,
    @PrimaryKey
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Double,
    @ColumnInfo(name = "windGust")
    val windGust: Double?,
    @Embedded
    val weather: WeatherEntity?,
    @ColumnInfo(name = "probabilityOfPrecipitation")
    val probabilityOfPrecipitation: Int?
){
    @Ignore
    constructor(current: Current) : this (
        temperature = current.temperature,
        maxTemperature = current.maxTemperature,
        minTemperature = current.minTemperature,
        relativeHumidity = current.relativeHumidity,
        windChill = current.windChill,
        windDirection = current.windDirection,
        windSpeed = current.windSpeed,
        windGust = current.windGust,
        weather = current.weather?.let { WeatherEntity(it) },
        probabilityOfPrecipitation = current.probabilityOfPrecipitation
    )
}