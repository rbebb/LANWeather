package com.example.lanweather.data.dao

import androidx.room.Transaction
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lanweather.data.entity.HourlyEntity

@Dao
interface HourlyEntityDao {

    @Query("SELECT * FROM Hourly")
    fun getHourly(): HourlyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHourly(hourly: HourlyEntity)

    @Transaction
    fun deleteAndInsert(hourly: HourlyEntity) {
        deleteAll()
        insertHourly(hourly)
    }

    @Query("DELETE FROM Hourly")
    fun deleteAll()
}