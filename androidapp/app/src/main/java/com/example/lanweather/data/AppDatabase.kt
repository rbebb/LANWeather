package com.example.lanweather.data

import android.view.ViewDebug
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lanweather.data.dao.CurrentEntityDao
import com.example.lanweather.data.dao.DailyEntityDao
import com.example.lanweather.data.dao.HourlyEntityDao
import com.example.lanweather.data.dao.SensorEntityDao
import com.example.lanweather.data.entity.CurrentEntity
import com.example.lanweather.data.entity.DailyEntity
import com.example.lanweather.data.entity.HourlyEntity
import com.example.lanweather.data.entity.SensorEntity


@Database(
    entities = [
        DailyEntity::class,
        HourlyEntity::class,
        CurrentEntity::class,
        SensorEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentEntityDao(): CurrentEntityDao

    abstract fun dailyEntityDao(): DailyEntityDao

    abstract fun hourlyEntityDao(): HourlyEntityDao

    abstract fun sensorEntityDao(): SensorEntityDao

}