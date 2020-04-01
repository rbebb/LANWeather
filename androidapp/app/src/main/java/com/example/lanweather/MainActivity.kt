package com.example.lanweather

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController;
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lanweather.data.AppDatabase
import com.example.lanweather.data.entity.SensorEntity
import com.example.lanweather.data.model.Nws
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import com.squareup.moshi.*


class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "lan_weather_notifications"
    private var notificationId = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_forecast, R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        println("testtestestestestestestestestestestesteststestestse")
        val dataBase = AppDatabase(this)//Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java).build()


        GlobalScope.launch {
            dataBase.sensorEntityDao().insertSensor(SensorEntity(37.0, 54.0))
            var data = dataBase.sensorEntityDao().getSensor()

            println(data)
            println("testtestestestestestestestestestestesteststestestse")
        }
        // Only used to test notifications
//        val button: Button = findViewById(R.id.test_notification)
//        button.setOnClickListener { displayNotification() }
    }

    private fun displayNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_cloud_black_24dp)
            .setContentTitle(getString(R.string.clothing))
            .setContentText(String.format(getString(R.string.wear_this_insert),
                getString(R.string.jacket)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel()

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
        notificationId++
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

    private fun getJsonFromFile(context: Context, fileName: String): String? {
        val json: String
        try {
            json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return json
    }

}
