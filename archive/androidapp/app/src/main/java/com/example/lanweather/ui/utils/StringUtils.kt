package com.example.lanweather.ui.utils

import java.lang.StringBuilder

class StringUtils {
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
    }
}