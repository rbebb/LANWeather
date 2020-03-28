package com.example.lanweather.data

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "Hourly")
data class Hourly (
    @Embedded
    val period: Period?
){
}