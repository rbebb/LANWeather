package com.example.lanweather.data.dao

import androidx.room.*
import com.example.lanweather.data.entity.CurrentEntity

@Dao
interface CurrentEntityDao {

    @Query("SELECT * FROM Current")
    fun getCurrent(): CurrentEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrent(current: CurrentEntity)

    @Transaction
    fun deleteAndInsert(current: CurrentEntity) {
        deleteAll()
        insertCurrent(current)
    }

    @Query("DELETE FROM Current")
    fun deleteAll()
}