package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "Current")
data class Current (
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
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Double?,
    @ColumnInfo(name = "windGust")
    val windGust: Double?,
    @Embedded
    val weather: Weather?,
    @ColumnInfo(name = "probabilityOfPrecipitation")
    val probabilityOfPrecipitation: Int?
){
}