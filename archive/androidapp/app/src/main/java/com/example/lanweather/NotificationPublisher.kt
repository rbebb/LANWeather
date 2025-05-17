package com.example.lanweather

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class NotificationPublisher : BroadcastReceiver() {

//    private var notificationId: Int = 1

    companion object {
        const val CHANNEL_ID = "lan_weather_notifications"
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION = "notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent!!.getIntExtra(NOTIFICATION_ID, 0)
        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION)
        println("Finally!")

        createNotificationChannel(context)

        with(context?.let { NotificationManagerCompat.from(it) }) {
            if (notification != null) {
                this?.notify(notificationId, notification)
            }
        }

//        with(context?.let { NotificationManagerCompat.from(it) }) {
//            if (notification != null) {
//                this?.notify(notificationId!!, notification)
//            }
//        }
//        notificationId++
    }

    private fun createNotificationChannel(context: Context?) {
        val name = context?.getString(R.string.channel_name)
        val descriptionText = context?.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}