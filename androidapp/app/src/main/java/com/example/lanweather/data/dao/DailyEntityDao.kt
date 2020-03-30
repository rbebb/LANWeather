package com.example.lanweather.data.dao

import androidx.room.*
import com.example.lanweather.data.entity.DailyEntity

@Dao
interface DailyEntityDao {

    @Query("SELECT * FROM Daily")
    fun getDaily(): DailyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDaily(daily: DailyEntity)

    @Transaction
    fun deleteAndInsert(daily: DailyEntity) {
        deleteAll()
        insertDaily(daily)
    }

    @Query("DELETE FROM Daily")
    fun deleteAll()
}