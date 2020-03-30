package com.example.lanweather.data.entity

import androidx.room.*
import com.example.lanweather.data.model.Hourly
import com.example.lanweather.data.model.Period

@Entity(tableName = "Hourly")
data class HourlyEntity (
    //@Embedded
    //val period: PeriodEntity?
    @PrimaryKey
    @ColumnInfo(name = "period")
    val period: List<Period>
){
    @Ignore
    constructor(hourly: Hourly) : this (
        period = hourly.period
    )
}