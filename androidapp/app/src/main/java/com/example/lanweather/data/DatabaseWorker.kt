package com.example.lanweather.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope


class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("weather.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    // Current Weather
                    val currentWeatherType = object : TypeToken<List<CurrentWeather>>() {}.type
                    val currentWeatherList: List<CurrentWeather> = Gson().fromJson(jsonReader, currentWeatherType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.currentWeatherDao().insertAll(currentWeatherList)

                    // Hourly Forecast
                    val hourlyWeatherType = object : TypeToken<List<HourlyForecast>>() {}.type
                    val hourlyWeatherList: List<HourlyForecast> = Gson().fromJson(jsonReader, hourlyWeatherType)

                    database.hourlyForecastDao().insertAll(hourlyWeatherList)

                    // Daily Forecast
                    val dailyWeatherType = object : TypeToken<List<DailyForecast>>() {}.type
                    val dailyWeatherList: List<DailyForecast> = Gson().fromJson(jsonReader, dailyWeatherType)

                    database.dailyForecastDao().insertAll(dailyWeatherList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Result.failure()
        }
    }


}