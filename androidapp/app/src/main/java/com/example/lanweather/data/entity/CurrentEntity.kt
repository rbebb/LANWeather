package com.example.lanweather.data.entity

import androidx.room.Ignore
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lanweather.data.model.Current

@Entity(tableName = "Current")
data class CurrentEntity (
    @ColumnInfo(name = "temperature")
    val temperature: Double? = 0.0,
    @ColumnInfo(name = "maxTemperature")
    val maxTemperature: Double? = 0.0,
    @ColumnInfo(name = "minTemperature")
    val minTemperature: Double? = 0.0,
    @ColumnInfo(name = "relativeHumidity")
    val relativeHumidity: Int? = 0,
    @ColumnInfo(name = "windChill")
    val windChill: Double?  = 0.0,
    @ColumnInfo(name = "windDirection")
    val windDirection: Double?  = 0.0,
    @PrimaryKey
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Double = 0.0,
    @ColumnInfo(name = "windGust")
    val windGust: Double? = 0.0,
    @Embedded
    val weather: WeatherEntity?,
    @ColumnInfo(name = "probabilityOfPrecipitation")
    val probabilityOfPrecipitation: Int? = 0
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