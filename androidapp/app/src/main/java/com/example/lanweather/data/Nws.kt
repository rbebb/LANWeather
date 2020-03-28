package com.example.lanweather.data

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "nws")
data class Nws (
    @Embedded
    val current: Current?,
    @Embedded
    val hourly: Hourly?,
    @Embedded
    val daily: Daily?
){

}