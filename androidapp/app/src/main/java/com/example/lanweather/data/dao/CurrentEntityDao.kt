package com.example.lanweather.data.dao

import androidx.room.Transaction
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lanweather.data.entity.CurrentEntity

@Dao
interface CurrentEntityDao {

    @Query("SELECT * FROM Current")
    suspend fun getCurrent(): CurrentEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrent(current: CurrentEntity)

    @Transaction
    suspend fun deleteAndInsert(current: CurrentEntity) {
        deleteAll()
        insertCurrent(current)
    }

    @Query("DELETE FROM Current")
    suspend fun deleteAll()
}