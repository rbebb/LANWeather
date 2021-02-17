package com.example.lanweather

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lanweather.data.AppDatabase
import com.example.lanweather.data.entity.CurrentEntity
import com.example.lanweather.data.entity.DailyEntity
import com.example.lanweather.data.entity.HourlyEntity
import com.example.lanweather.data.entity.SensorEntity
import com.example.lanweather.data.model.Current
import com.example.lanweather.data.model.Daily
import com.example.lanweather.data.model.Hourly
import com.example.lanweather.data.model.Sensor
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import org.json.JSONObject
import org.zeromq.SocketType
import zmq.ZMQ

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "lan_weather_notifications"
    private var notificationId: Int = 1

    companion object {
        private lateinit var instance: MainActivity

        fun getInstance(): MainActivity {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_forecast, R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val database = AppDatabase(this)

        CoroutineScope(CoroutineName("GetData")).launch {
            val context: org.zeromq.ZMQ.Context = org.zeromq.ZMQ.context(1)
            val socket: org.zeromq.ZMQ.Socket = context.socket(SocketType.type(ZMQ.ZMQ_REQ))
            socket.connect("tcp://lw.minifigone.com:5680")
            socket.send("launch the nukes")
            val jsonData: String = socket.recvStr()
            socket.close()

            getCurrentWeatherData(database, jsonData)
            getDailyData(database, jsonData)
            getHourlyData(database, jsonData)
            getSensorData(database, jsonData)
        }

        // Only used to test notifications
//        val button: Button = findViewById(R.id.test_notification)
//        button.setOnClickListener { displayNotification() }
    }

    private suspend fun getCurrentWeatherData(database: AppDatabase, jsonData: String) {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("current")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val currentAdapter: JsonAdapter<Current> = moshi.adapter(Current::class.java)
        val current: Current? = getCurrent(currentAdapter, jsonDataObj.toString())
        if (current != null) {
            database.currentEntityDao().deleteAndInsert(CurrentEntity(current))
        }
    }

    private suspend fun getCurrent(adapter: JsonAdapter<Current>,
                                       json: String): Current? = withContext(Dispatchers.IO) {
        return@withContext adapter.fromJson(json)
    }

    private suspend fun getDailyData(database: AppDatabase, jsonData: String) {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("daily")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val dailyAdapter: JsonAdapter<Daily> = moshi.adapter(Daily::class.java)
        val daily: Daily? = getDaily(dailyAdapter, jsonDataObj.toString())
        if (daily != null) {
            database.dailyEntityDao().deleteAndInsert(DailyEntity(daily))
        }
    }

    private suspend fun getDaily(adapter: JsonAdapter<Daily>,
                                   json: String): Daily? = withContext(Dispatchers.IO) {
        return@withContext adapter.fromJson(json)
    }

    private suspend fun getHourlyData(database: AppDatabase, jsonData: String) {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("hourly")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val hourlyAdapter: JsonAdapter<Hourly> = moshi.adapter(Hourly::class.java)
        val hourly: Hourly? = getHourly(hourlyAdapter, jsonDataObj.toString())
        if (hourly != null) {
            database.hourlyEntityDao().deleteAndInsert(HourlyEntity(hourly))
        }
    }

    private suspend fun getHourly(adapter: JsonAdapter<Hourly>,
                                 json: String): Hourly? = withContext(Dispatchers.IO) {
        return@withContext adapter.fromJson(json)
    }

    private suspend fun getSensorData(database: AppDatabase, jsonData: String) {
        // Get the nested JSON object
        val jsonDataObj = JSONObject(jsonData).get("sensor")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val sensorAdapter: JsonAdapter<Sensor> = moshi.adapter(Sensor::class.java)
        val sensor: Sensor? = getSensor(sensorAdapter, jsonDataObj.toString())
        if (sensor != null) {
            database.sensorEntityDao().deleteAndInsert(SensorEntity(sensor))
        }
    }

    private suspend fun getSensor(adapter: JsonAdapter<Sensor>,
                                   json: String): Sensor? = withContext(Dispatchers.IO) {
        return@withContext adapter.fromJson(json)
    }

    private fun testNotification() {
        val notificationIntent = Intent(this, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(getString(R.string.jacket)))
        sendBroadcast(notificationIntent)
        notificationId++
    }

    private fun getNotification(content: String): Notification {
        return NotificationCompat.Builder(this, NotificationPublisher.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cloud_black_24dp)
            .setContentTitle(getString(R.string.clothing))
            .setContentText(String.format(getString(R.string.wear_this_insert),
                content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setChannelId(NotificationPublisher.CHANNEL_ID)
            .build()
    }

    private fun displayNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cloud_black_24dp)
            .setContentTitle(getString(R.string.clothing))
            .setContentText(String.format(getString(R.string.wear_this_insert),
                getString(R.string.sweatshirt)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel()

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
        notificationId++
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
