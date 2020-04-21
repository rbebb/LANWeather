package com.example.lanweather.ui.home

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
import com.example.lanweather.data.entity.SensorEntity
import com.example.lanweather.data.model.Period
import kotlinx.coroutines.*
import java.lang.StringBuilder
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
//    private val mainActivity: MainActivity = MainActivity.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.home_fragment, container, false)

        val database = AppDatabase(MainActivity.getInstance().baseContext)

        CoroutineScope(CoroutineName("PopulateHome")).launch {
            withContext(Dispatchers.IO) {
                // Incorporated this delay to allow the coroutine in the MainActivity to finish
                // The app currently crashes without this
                delay(2000)

                val sensor: SensorEntity = database.sensorEntityDao().getSensor()
                val curTemp: Double = sensor.temperature
                val curTempF: Double = curTemp * 9/5 + 32.0
                val humidity: Double? = sensor.humidity

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

                val hourlyList: List<Period> = database.hourlyEntityDao().getHourly().periods
                val smallHourlyList = arrayListOf(hourlyList[0], hourlyList[1], hourlyList[2])

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
                    val imageViewCurrent: ImageView = root.findViewById(R.id.current_weather_icon)
                    if (weather.toLowerCase().contains("rain")) {
                        imageViewCurrent.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_rain))
                    }
                    else if (weather.toLowerCase().contains("partly")) {
                        imageViewCurrent.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day_1))
                    }
                    else if (weather.toLowerCase().contains("mostly")) {
                        imageViewCurrent.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day))
                    }
                    else if (weather.toLowerCase().contains("sunny")) {
                        imageViewCurrent.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_sun))
                    }
                    else if (weather.toLowerCase().contains("snow")) {
                        imageViewCurrent.setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_snowing_1))
                    }
                    
                    val textViewOverview: TextView = root.findViewById(R.id.home_overview_text)
                    textViewOverview.text = String.format(getString(R.string.home_overview), humidity, curTempF)

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

                    hourly(root, smallHourlyList)
                }
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun hourly(root: View, smallHourlyList: ArrayList<Period>) {
        val hourTextViews = arrayListOf<TextView>(
            root.findViewById(R.id.card_hourly_overview_hour_1),
            root.findViewById(R.id.card_hourly_overview_hour_2),
            root.findViewById(R.id.card_hourly_overview_hour_3)
        )

        val hourImageViews = arrayListOf<ImageView>(
            root.findViewById(R.id.card_hourly_overview_hour_1_icon),
            root.findViewById(R.id.card_hourly_overview_hour_2_icon),
            root.findViewById(R.id.card_hourly_overview_hour_3_icon)
        )

        val hourTempTextViews = arrayListOf<TextView>(
            root.findViewById(R.id.card_hourly_overview_hour_1_temp),
            root.findViewById(R.id.card_hourly_overview_hour_2_temp),
            root.findViewById(R.id.card_hourly_overview_hour_3_temp)
        )
        for (i in smallHourlyList.indices) {
            val weather: String? = smallHourlyList[i].shortForecast
            val startTime: String? = smallHourlyList[i].startTime
            hourTextViews[i].text = startTime?.substring(11, 16)

            if (weather!!.toLowerCase().contains("rain")) {
                hourImageViews[i].setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_rain))
            }
            else if (weather.toLowerCase().contains("partly")) {
                hourImageViews[i].setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day_1))
            }
            else if (weather.toLowerCase().contains("mostly")) {
                hourImageViews[i].setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_cloudy_day))
            }
            else if (weather.toLowerCase().contains("sunny")) {
                hourImageViews[i].setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_sun))
            }
            else if (weather.toLowerCase().contains("snow")) {
                hourImageViews[i].setImageDrawable(MainActivity.getInstance().getDrawable(R.drawable.ic_snowing_1))
            }

            val maxTemp: Int = smallHourlyList[i].temperature!!
            val maxTempF: Double = maxTemp * 9/5 + 32.0
            hourTempTextViews[i].text = String.format("%sº", maxTempF.toInt().toString())
        }
    }

}
