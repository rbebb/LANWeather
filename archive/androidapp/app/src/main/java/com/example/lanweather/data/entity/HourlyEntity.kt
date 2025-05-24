package com.example.lanweather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.lanweather.data.model.Hourly
import com.example.lanweather.data.model.Period

@Entity(tableName = "Hourly")
data class HourlyEntity (
    //@Embedded
    //val periods: PeriodEntity?
    @PrimaryKey
    @ColumnInfo(name = "periods")
    val periods: List<Period>
){
    @Ignore
    constructor(hourly: Hourly) : this (
        periods = hourly.periods
    )
}