package com.example.lanweather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DailyForecastDao {
    // Query may be adjusted based on data volume and update frequency
    @Query("SELECT * FROM daily")
    fun getDailyForecast(): List<DailyForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dailies: List<DailyForecast>)
}