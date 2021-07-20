import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:flutterapp/models/current.dart';

Future<Current?> fetchAllWeatherData() async {
  const platform =
      const MethodChannel("com.lanweather.flutterapp/fetchallweatherdata");
  const String url = "tcp://192.168.83.3:5680";
  const String data = "launch the nukes";

  try {
    final String result = await platform
            .invokeMethod("fetchAllWeatherData", {"url": url, "data": data}) ??
        "";
    final resultJson = jsonDecode(result);
    return Current.fromJson(resultJson);
  } on PlatformException catch (e) {
    "Failed to get all weather data: '${e.message}'";
  }
}
