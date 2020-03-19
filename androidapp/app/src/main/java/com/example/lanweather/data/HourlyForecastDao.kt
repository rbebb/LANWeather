package com.example.lanweather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HourlyForecastDao {
    // Query may be adjusted based on data volume and update frequency
    @Query("SELECT * FROM hourly")
    fun getHourlyForecast(): List<HourlyForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hourlies: List<HourlyForecast>)
}