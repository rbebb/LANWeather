import 'package:shared_preferences/shared_preferences.dart';

class Preferences {
  static late SharedPreferencesWithCache _preferences;

  static Future<void> init() async {
    _preferences = await SharedPreferencesWithCache.create(
      cacheOptions: const SharedPreferencesWithCacheOptions(allowList: <String>{"weather_server_url"}),
    );
  }

  static String getWeatherServerUrl() {
    return _preferences.getString("weather_server_url") ?? "tcp://localhost:5680";
  }

  static void setWeatherServerUrl(String url) {
    _preferences.setString("weather_server_url", url);
  }
}
