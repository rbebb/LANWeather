package com.example.lanweather.ui.forecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lanweather.MainActivity
import com.example.lanweather.R
import com.example.lanweather.data.AppDatabase
import com.example.lanweather.data.entity.CurrentEntity
import com.example.lanweather.data.entity.DailyEntity
import com.example.lanweather.data.entity.SensorEntity
import com.example.lanweather.data.model.Period
import kotlinx.coroutines.*
import java.lang.StringBuilder

class ForecastFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastFragment()
    }

    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.forecast_fragment, container, false)

        val database = AppDatabase(MainActivity.getInstance().baseContext)

        CoroutineScope(CoroutineName("PopulateForecast")).launch {
            withContext(Dispatchers.IO) {
                val current: CurrentEntity = database.currentEntityDao().getCurrent()
                var weather: String? = current.weather?.weather
                if (weather == null) {
                    weather = "sunny"
                }
                val dayMaxTemp: Double = current.maxTemperature!!
                val dayMaxTempF: Double = dayMaxTemp * 9/5 + 32.0

                val tomorrow: Period = database.dailyEntityDao().getDaily().periods[1]
                val tomorrowWeather: String? = tomorrow.shortForecast
                val tomorrowMaxTemp: Int = tomorrow.temperature!!
                val tomorrowMaxTempF: Double = tomorrowMaxTemp * 9/5 + 32.0

                val dailyList: List<Period> = database.dailyEntityDao().getDaily().periods

                val stringBuilder = StringBuilder()
                for (i in weather!!.indices) {
                    if (i == 0) {
                        stringBuilder.append(weather[i].toUpperCase())
                    }
                    else if (weather[i] == '_') {
                        stringBuilder.append(' ')
                    }
                    else {
                        stringBuilder.append(weather[i])
                    }
                }
                weather = stringBuilder.toString()

                withContext(Dispatchers.Main) {
                    val textViewToday: TextView = root.findViewById(R.id.card_today_content_text)
                    textViewToday.text = String.format(getString(R.string.today_details), weather, dayMaxTempF)

                    val imageViewToday: ImageView = root.findViewById(R.id.card_today_content_icon)
                    if (weather.toLowerCase().contains("rain")) {
                        imageViewToday.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_rain))
                    }
                    else if (weather.toLowerCase().contains("partly")) {
                        imageViewToday.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day_1))
                    }
                    else if (weather.toLowerCase().contains("mostly")) {
                        imageViewToday.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day))
                    }
                    else if (weather.toLowerCase().contains("sunny")) {
                        imageViewToday.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_sun))
                    }
                    else if (weather.toLowerCase().contains("snow")) {
                        imageViewToday.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_snowing_1))
                    }

                    val textViewTomorrow: TextView = root.findViewById(R.id.card_tomorrow_content_text)
                    textViewTomorrow.text = String.format(getString(R.string.tomorrow_details), tomorrowWeather, tomorrowMaxTempF)

                    val imageViewTomorrow: ImageView = root.findViewById(R.id.card_tomorrow_content_icon)
                    if (tomorrowWeather!!.toLowerCase().contains("rain")) {
                        imageViewTomorrow.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_rain))
                    }
                    else if (tomorrowWeather.toLowerCase().contains("partly")) {
                        imageViewTomorrow.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day_1))
                    }
                    else if (tomorrowWeather.toLowerCase().contains("mostly")) {
                        imageViewTomorrow.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day))
                    }
                    else if (tomorrowWeather.toLowerCase().contains("sunny")) {
                        imageViewTomorrow.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_sun))
                    }
                    else if (tomorrowWeather.toLowerCase().contains("snow")) {
                        imageViewTomorrow.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_snowing_1))
                    }

                    daily(root, dailyList)
                }
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun daily(root: View, dailyList: List<Period>) {
        val dailyTextViews = arrayListOf<TextView>(
            root.findViewById(R.id.card_forecast_overview_day_1),
            root.findViewById(R.id.card_forecast_overview_day_2),
            root.findViewById(R.id.card_forecast_overview_day_3),
            root.findViewById(R.id.card_forecast_overview_day_4),
            root.findViewById(R.id.card_forecast_overview_day_5),
            root.findViewById(R.id.card_forecast_overview_day_6),
            root.findViewById(R.id.card_forecast_overview_day_7)
        )

        val dailyImageViews = arrayListOf<ImageView>(
            root.findViewById(R.id.card_forecast_overview_day_1_icon),
            root.findViewById(R.id.card_forecast_overview_day_2_icon),
            root.findViewById(R.id.card_forecast_overview_day_3_icon),
            root.findViewById(R.id.card_forecast_overview_day_4_icon),
            root.findViewById(R.id.card_forecast_overview_day_5_icon),
            root.findViewById(R.id.card_forecast_overview_day_6_icon),
            root.findViewById(R.id.card_forecast_overview_day_7_icon)
        )

        val dailyTempTextViews = arrayListOf<TextView>(
            root.findViewById(R.id.card_forecast_overview_day_1_temp),
            root.findViewById(R.id.card_forecast_overview_day_2_temp),
            root.findViewById(R.id.card_forecast_overview_day_3_temp),
            root.findViewById(R.id.card_forecast_overview_day_4_temp),
            root.findViewById(R.id.card_forecast_overview_day_5_temp),
            root.findViewById(R.id.card_forecast_overview_day_6_temp),
            root.findViewById(R.id.card_forecast_overview_day_7_temp)
        )

        var skip = false
        var innerListIterator = 0
        for (i in dailyList.indices) {
            val weather: String? = dailyList[i].shortForecast
            if (dailyList[i].name!! == "Tonight" || dailyList[i].name == "Overnight") {
                if (skip) {
                    skip = false
                }
                continue
            }
            if (skip) {
                skip = false
                continue
            }
            skip = true
            dailyTextViews[innerListIterator].text = dailyList[i].name

            if (weather!!.toLowerCase().contains("rain")) {
                dailyImageViews[innerListIterator].setImageDrawable(
                    MainActivity.getInstance().getDrawable(R.drawable.ic_rain)
                )
            } else if (weather.toLowerCase().contains("partly")) {
                dailyImageViews[innerListIterator].setImageDrawable(
                    MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day_1)
                )
            } else if (weather.toLowerCase().contains("mostly")) {
                dailyImageViews[innerListIterator].setImageDrawable(
                    MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day)
                )
            } else if (weather.toLowerCase().contains("sunny")) {
                dailyImageViews[innerListIterator].setImageDrawable(
                    MainActivity.getInstance().getDrawable(R.drawable.ic_sun)
                )
            } else if (weather.toLowerCase().contains("snow")) {
                dailyImageViews[innerListIterator].setImageDrawable(
                    MainActivity.getInstance().getDrawable(R.drawable.ic_snowing_1)
                )
            }

            val maxTemp: Int = dailyList[i].temperature!!
            val maxTempF: Double = maxTemp * 9 / 5 + 32.0
            dailyTempTextViews[innerListIterator].text = String.format("%sº", maxTempF.toInt().toString())
            innerListIterator++
        }
    }
}
