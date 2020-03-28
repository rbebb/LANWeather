package com.example.lanweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Weather")
data class Weather (
    @ColumnInfo(name = "coverage")
    val coverage: String?,
    @ColumnInfo(name = "weather")
    val weather: String?,
    @ColumnInfo(name = "intensity")
    val intensity: String?,
    @ColumnInfo(name = "visibility")
    val visibility: Double?
){
}