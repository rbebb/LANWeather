package com.example.lanweather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {
    // Query may be adjusted based on data volume and update frequency
    @Query("SELECT * FROM current_weather")
    fun getCurrentWeather(): List<CurrentWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weather: List<CurrentWeather>)
}