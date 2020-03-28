package com.example.lanweather.data

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "Daily")
data class Daily (
    @Embedded
    val period: Period?
){
}