package com.example.lanweather.data.dao

import androidx.room.Transaction
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lanweather.data.entity.DailyEntity

@Dao
interface DailyEntityDao {

    @Query("SELECT * FROM Daily")
    suspend fun getDaily(): DailyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(daily: DailyEntity)

    @Transaction
    suspend fun deleteAndInsert(daily: DailyEntity) {
        deleteAll()
        insertDaily(daily)
    }

    @Query("DELETE FROM Daily")
    suspend fun deleteAll()
}