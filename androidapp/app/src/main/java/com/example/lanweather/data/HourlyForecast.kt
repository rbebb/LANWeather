package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly")
data class HourlyForecast(

    //    Subject to change once I know what the backend is pumping out

    @PrimaryKey @ColumnInfo(name = "hour") val hour: Int,
    val temperature: Int,
    val weather: Int) {

    fun getTheWeather(): String {
        return when (weather) {
            0 -> "Sunny"
            1 -> "Cloudy"
            2 -> "Partly Cloudy"
            3 -> "Snowing"
            4 -> "Raining"
            else -> "Sunny"
        }
    }
}