package com.example.lanweather.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.example.lanweather.data.model.Nws

@Entity(tableName = "nws")
data class NwsEntity (
    @Embedded
    val current: CurrentEntity,
    @Embedded
    val hourly: HourlyEntity,
    @Embedded
    val daily: DailyEntity
){
    @Ignore
    constructor(nws : Nws) : this (
        current = CurrentEntity(nws.current),
        hourly = HourlyEntity(nws.hourly),
        daily = DailyEntity(nws.daily)
    )
}