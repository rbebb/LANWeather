package com.example.lanweather.data

import androidx.room.TypeConverter
import com.example.lanweather.data.model.Period
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object DataConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<Period>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Period::class.java)
        val adapter = moshi.adapter<List<Period>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<Period>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Period::class.java)
        val adapter = moshi.adapter<List<Period>>(type)
        return adapter.toJson(objects)
    }

}