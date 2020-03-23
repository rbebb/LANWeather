package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeather (

    //    Subject to change once I know what the backend is pumping out

    @PrimaryKey @ColumnInfo(name = "entry")
    val entry: Int = 0,
    val pullTime: Int,
    val humidity: Int,
    val wind: Int,
    val realFeel: Int,
    val temperature: Int) {
}