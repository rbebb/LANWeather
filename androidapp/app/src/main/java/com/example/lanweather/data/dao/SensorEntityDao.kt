package com.example.lanweather.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.lanweather.data.entity.SensorEntity

@Dao
interface SensorEntityDao {

    @Query("SELECT * FROM Sensor")
    fun getSensor(): SensorEntity

    @Insert(onConflict = REPLACE)
    fun insertSensor(sensor: SensorEntity)

    @Transaction
    fun deleteAndInsert(sensor: SensorEntity) {
        deleteAll()
        insertSensor(sensor)
    }

    @Query("DELETE FROM Sensor")
    fun deleteAll()

}