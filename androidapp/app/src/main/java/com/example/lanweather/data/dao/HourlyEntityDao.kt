package com.example.lanweather.data.dao

import androidx.room.*
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