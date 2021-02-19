package com.example.lanweather.ui.forecast

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
import com.example.lanweather.data.model.Period
import com.example.lanweather.ui.utils.ImageUtils
import com.example.lanweather.ui.utils.StringUtils
import kotlinx.coroutines.*

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
                var tomorrowWeather: String? = tomorrow.shortForecast
                if (tomorrowWeather == null) {
                    tomorrowWeather = "sunny"
                }
                val tomorrowMaxTemp: Int = tomorrow.temperature!!
                val tomorrowMaxTempF: Double = tomorrowMaxTemp * 9/5 + 32.0

                val dailyList: List<Period> = database.dailyEntityDao().getDaily().periods

                weather = StringUtils.getFormattedWeather(weather)

                withContext(Dispatchers.Main) {
                    val textViewToday: TextView = root.findViewById(R.id.card_today_content_text)
                    textViewToday.text = String.format(getString(R.string.today_details), weather, dayMaxTempF)

                    val imageViewToday: ImageView = root.findViewById(R.id.card_today_content_icon)
                    ImageUtils.setWeatherIcon(weather, imageViewToday, resources, context)

                    val textViewTomorrow: TextView = root.findViewById(R.id.card_tomorrow_content_text)
                    textViewTomorrow.text = String.format(getString(R.string.tomorrow_details), tomorrowWeather, tomorrowMaxTempF)

                    val imageViewTomorrow: ImageView = root.findViewById(R.id.card_tomorrow_content_icon)
                    ImageUtils.setWeatherIcon(tomorrowWeather, imageViewTomorrow, resources, context)

                    daily(root, dailyList)
                }
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun daily(root: View, dailyList: List<Period>) {
        val dailyTextViews = mutableListOf<TextView>()
        for (i in 1..7) {
            val viewId = resources.getIdentifier("card_forecast_overview_day_${i}_temp", "id", context?.packageName)
            dailyTextViews.add(root.findViewById(viewId))
        }

        val dailyImageViews = mutableListOf<ImageView>()
        for (i in 1..7) {
            val viewId = resources.getIdentifier("card_forecast_overview_day_${i}_temp", "id", context?.packageName)
            dailyImageViews.add(root.findViewById(viewId))
        }

        val dailyTempTextViews = mutableListOf<TextView>()
        for (i in 1..7) {
            val viewId = resources.getIdentifier("card_forecast_overview_day_${i}_temp", "id", context?.packageName)
            dailyTempTextViews.add(root.findViewById(viewId))
        }

        var skip = false
        var innerListIterator = 0
        for (i in dailyList.indices) {
            var weather: String? = dailyList[i].shortForecast
            if (weather == null) {
                weather = "sunny"
            }
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

            ImageUtils.setWeatherIcon(weather, dailyImageViews[innerListIterator], resources, context)

            val maxTemp: Int = dailyList[i].temperature!!
            val maxTempF: Double = maxTemp * 9 / 5 + 32.0
            dailyTempTextViews[innerListIterator].text = String.format("%sº", maxTempF.toInt().toString())
            innerListIterator++
        }
    }
}
