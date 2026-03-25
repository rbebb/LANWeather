import 'dart:convert';

import 'package:dartzmq/dartzmq.dart';
import 'package:lanweatherapp/services/preferences.dart';

Future<Map<String, dynamic>?> fetchAllWeatherData() async {
  const String data = "launch the nukes";
  String url = Preferences.getWeatherServerUrl();

  try {
    // TODO: Do we need to specify the number of threads?
    final ZContext context = ZContext();
    final ZSocket socket = context.createSocket(SocketType.req);
    socket.connect(url);
    socket.sendString(data);
    final response = await socket.payloads.first;
    final jsonData = utf8.decode(response);
    final resultJson = jsonDecode(jsonData);
    // final String result = await platform.invokeMethod("fetchAllWeatherData", {"url": url, "data": data}) ?? "";
    // print(resultJson);
    return resultJson;
  } on Exception catch (e) {
    print("Failed to get all weather data: '${e}'");
    return null;
  }
}
