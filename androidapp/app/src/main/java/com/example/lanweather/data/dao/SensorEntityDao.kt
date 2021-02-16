package com.example.lanweather.data.dao

import androidx.room.Transaction
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import com.example.lanweather.data.entity.SensorEntity

@Dao
interface SensorEntityDao {

    @Query("SELECT * FROM Sensor")
    suspend fun getSensor(): SensorEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertSensor(sensor: SensorEntity)

    @Transaction
    suspend fun deleteAndInsert(sensor: SensorEntity) {
        deleteAll()
        insertSensor(sensor)
    }

    @Query("DELETE FROM Sensor")
    suspend fun deleteAll()

}