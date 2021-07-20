package com.lanweather.flutterapp

import android.os.Build
import android.os.StrictMode
import androidx.lifecycle.lifecycleScope
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject
import org.zeromq.SocketType
import zmq.ZMQ

class MainActivity: FlutterActivity() {
    companion object {
        private const val FETCH_ALL_WEATHER_DATA_CHANNEL = "com.lanweather.flutterapp/fetchallweatherdata"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, FETCH_ALL_WEATHER_DATA_CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "fetchAllWeatherData") {
                val url = call.argument<String>("url")
                val data = call.argument<String>("data")

                // Android 8.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
                    StrictMode.setThreadPolicy(policy)
                }

                lifecycleScope.launchWhenStarted {
                    val context: org.zeromq.ZMQ.Context = org.zeromq.ZMQ.context(1)
                    val socket: org.zeromq.ZMQ.Socket = context.socket(SocketType.type(ZMQ.ZMQ_REQ))
                    socket.connect(url)
                    socket.send(data)
                    val jsonData: String = socket.recvStr()
                    socket.close()

                    // Return the JSON data to the Flutter code
                    result.success(getCurrentData(jsonData))
                }
            }
        }
    }

    /**
     * Get weather data for the current time
     *
     * @param jsonData: the JSON data
     * @return the specific stringified JSON data
     */
    private fun getCurrentData(jsonData: String): String {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("current")
        return jsonDataObj.toString()
    }

    /**
     * Get the daily weather data
     *
     * @param jsonData: the JSON data
     * @return the specific stringified JSON data
     */
    private fun getDailyData(jsonData: String): String {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("daily")
        return jsonDataObj.toString()
    }

    /**
     * Get the hourly weather data
     *
     * @param jsonData: the JSON data
     * @return the specific stringified JSON data
     */
    private fun getHourlyData(jsonData: String): String {
        // Get the nested JSON object
        var jsonDataObj = JSONObject(jsonData).get("nws")
        jsonDataObj = JSONObject(jsonDataObj.toString()).get("hourly")
        return jsonDataObj.toString()
    }

    /**
     * Get the sensor data
     *
     * @param jsonData: the JSON data
     * @return the specific stringified JSON data
     */
    private fun getSensorData(jsonData: String): String {
        val jsonDataObj = JSONObject(jsonData).get("sensor")
        return jsonDataObj.toString()
    }
}
