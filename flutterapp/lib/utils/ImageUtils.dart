class ImageUtils {
  static String? getWeatherIconAsset(String weather) {
    final lowerWeather = weather.toLowerCase();

    if (lowerWeather.contains("rain")) {
      return "assets/weather_icons/ic_rain.png";
    } else if (lowerWeather.contains("partly")) {
      return "assets/weather_icons/ic_cloudy_day_1.png";
    } else if (lowerWeather.contains("mostly")) {
      return "assets/weather_icons/ic_cloudy_day.png";
    } else if (lowerWeather.contains("sunny")) {
      return "assets/weather_icons/ic_sun.png";
    } else if (lowerWeather.contains("snow")) {
      return "assets/weather_icons/ic_snowing_1.png";
    }

    return null;
  }
}
