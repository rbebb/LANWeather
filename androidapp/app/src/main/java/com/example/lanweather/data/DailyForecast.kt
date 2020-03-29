package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class DailyForecast(

    //    Subject to change once I know what the backend is pumping out

    @PrimaryKey @ColumnInfo(name = "day") val day: Int,
    val temperature: Int,
    val weather: Int) {

    fun getWeather(): String {
        return when (weather) {
            0 -> "Sunny"
            1 -> "Cloudy"
            2 -> "Partly Cloudy"
            3 -> "Snowing"
            4 -> "Raining"
            else -> "Sunny"
        }
    }

    fun getDay(): String {
        return when (day) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Tomorrow"
        }
    }
}