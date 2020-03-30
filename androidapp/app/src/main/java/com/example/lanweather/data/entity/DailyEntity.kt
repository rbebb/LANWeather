package com.example.lanweather.data.entity

import androidx.room.*
import com.example.lanweather.data.model.Daily
import com.example.lanweather.data.model.Period

@Entity(tableName = "Daily")
data class DailyEntity (
    //@Embedded
    //val period: PeriodEntity?
    @PrimaryKey
    @ColumnInfo(name = "period")
    val period: List<Period>
){
    @Ignore
    constructor(daily: Daily) : this (
        period = daily.period
    )
}