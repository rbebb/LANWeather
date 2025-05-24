package com.example.lanweather.ui.utils

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.lanweather.R

class ImageUtils {
    companion object {
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