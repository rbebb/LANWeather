import 'dart:convert';

import 'package:flutter/services.dart';

Future<dynamic?> fetchAllWeatherData() async {
  const platform = const MethodChannel("com.lanweather.flutterapp/fetchallweatherdata");
  const String url = "tcp://192.168.99.178:5680";
  const String data = "launch the nukes";

  try {
    final String result = await platform.invokeMethod("fetchAllWeatherData", {"url": url, "data": data}) ?? "";
    final resultJson = jsonDecode(result);
    // print(resultJson);
    return resultJson;
  } on PlatformException catch (e) {
    print("Failed to get all weather data: '${e.message}'");
    return null;
  }
}
