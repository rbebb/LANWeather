package com.example.lanweather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.lanweather.data.model.Sensor

@Entity(tableName = "Sensor")
data class SensorEntity (
    @PrimaryKey
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Double?
){
    @Ignore
    constructor(sensor: Sensor) : this (
        temperature = sensor.temperature,
        humidity = sensor.humidity
    )
}