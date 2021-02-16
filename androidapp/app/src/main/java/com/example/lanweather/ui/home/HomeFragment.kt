package com.example.lanweather.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lanweather.MainActivity
import com.example.lanweather.R
import com.example.lanweather.data.AppDatabase
import com.example.lanweather.data.entity.CurrentEntity
import com.example.lanweather.data.entity.SensorEntity
import com.example.lanweather.data.model.Period
import com.example.lanweather.ui.CommonUtils
import kotlinx.coroutines.*

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
            ViewModelProvider(this).get(HomeViewModel::class.java)

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
                var tomorrowWeather: String? = tomorrow.shortForecast
                if (tomorrowWeather == null) {
                    tomorrowWeather = "sunny"
                }
                val tomorrowMaxTemp: Int = tomorrow.temperature!!
                val tomorrowMaxTempF: Double = tomorrowMaxTemp * 9/5 + 32.0

                val hourlyList: List<Period> = database.hourlyEntityDao().getHourly().periods
                val smallHourlyList = listOf(hourlyList[0], hourlyList[1], hourlyList[2])

                weather = CommonUtils.getFormattedWeather(weather)

                withContext(Dispatchers.Main) {
                    val imageViewCurrent: ImageView = root.findViewById(R.id.current_weather_icon)
                    CommonUtils.setWeatherIcon(weather, imageViewCurrent, resources, context)

                    val textViewOverview: TextView = root.findViewById(R.id.home_overview_text)
                    textViewOverview.text = String.format(getString(R.string.home_overview), humidity, curTempF)

                    val textViewToday: TextView = root.findViewById(R.id.card_today_content_text)
                    textViewToday.text = String.format(getString(R.string.today_details), weather, dayMaxTempF)

                    val imageViewToday: ImageView = root.findViewById(R.id.card_today_content_icon)
                    CommonUtils.setWeatherIcon(weather, imageViewToday, resources, context)

                    val textViewTomorrow: TextView = root.findViewById(R.id.card_tomorrow_content_text)
                    textViewTomorrow.text = String.format(getString(R.string.tomorrow_details), tomorrowWeather, tomorrowMaxTempF)

                    val imageViewTomorrow: ImageView = root.findViewById(R.id.card_tomorrow_content_icon)
                    CommonUtils.setWeatherIcon(tomorrowWeather, imageViewTomorrow, resources, context)

                    hourly(root, smallHourlyList)
                }
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun hourly(root: View, smallHourlyList: List<Period>) {
        val test = mutableListOf<TextView>()
        for (i in 1..3) {
            val viewId = resources.getIdentifier("card_hourly_overview_hour_${i}", "id", context?.packageName)
            test.add(root.findViewById(viewId))
        }
        print("HOWDY $test")

        val hourTextViews = listOf<TextView>(
            root.findViewById(R.id.card_hourly_overview_hour_1),
            root.findViewById(R.id.card_hourly_overview_hour_2),
            root.findViewById(R.id.card_hourly_overview_hour_3)
        )

        val hourImageViews = listOf<ImageView>(
            root.findViewById(R.id.card_hourly_overview_hour_1_icon),
            root.findViewById(R.id.card_hourly_overview_hour_2_icon),
            root.findViewById(R.id.card_hourly_overview_hour_3_icon)
        )

        val hourTempTextViews = listOf<TextView>(
            root.findViewById(R.id.card_hourly_overview_hour_1_temp),
            root.findViewById(R.id.card_hourly_overview_hour_2_temp),
            root.findViewById(R.id.card_hourly_overview_hour_3_temp)
        )
        for (i in smallHourlyList.indices) {
            var weather: String? = smallHourlyList[i].shortForecast
            if (weather == null) {
                weather = "sunny"
            }
            val startTime: String? = smallHourlyList[i].startTime
            hourTextViews[i].text = startTime?.substring(11, 16)

            CommonUtils.setWeatherIcon(weather, hourImageViews[i], resources, context)

            val maxTemp: Int = smallHourlyList[i].temperature!!
            val maxTempF: Double = maxTemp * 9/5 + 32.0
            hourTempTextViews[i].text = String.format("%sº", maxTempF.toInt().toString())
        }
    }

}
