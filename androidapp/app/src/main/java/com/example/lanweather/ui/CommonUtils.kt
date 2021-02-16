package com.example.lanweather.ui

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.lanweather.R
import java.lang.StringBuilder

class CommonUtils {
    companion object {
        fun getFormattedWeather(weather: String): String {
            val stringBuilder = StringBuilder()
            for (i in weather.indices) {
                when {
                    i == 0 -> {
                        stringBuilder.append(weather[i].toUpperCase())
                    }
                    weather[i] == '_' -> {
                        stringBuilder.append(' ')
                    }
                    else -> {
                        stringBuilder.append(weather[i])
                    }
                }
            }
            return stringBuilder.toString()
        }

        fun setWeatherIcon(weather: String, imageView: ImageView, resources: Resources, context: Context?) {
            when {
                weather.toLowerCase().contains("rain") -> {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_rain, context?.theme))
                }
                weather.toLowerCase().contains("partly") -> {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_cloudy_day_1, context?.theme))
                }
                weather.toLowerCase().contains("mostly") -> {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_cloudy_day, context?.theme))
                }
                weather.toLowerCase().contains("sunny") -> {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_sun, context?.theme))
                }
                weather.toLowerCase().contains("snow") -> {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_snowing_1, context?.theme))
                }
            }
        }
    }
}