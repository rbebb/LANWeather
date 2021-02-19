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
    suspend fun getHourly(): HourlyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourly(hourly: HourlyEntity)

    @Transaction
    suspend fun deleteAndInsert(hourly: HourlyEntity) {
        deleteAll()
        insertHourly(hourly)
    }

    @Query("DELETE FROM Hourly")
    suspend fun deleteAll()
}