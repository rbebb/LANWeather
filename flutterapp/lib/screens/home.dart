import 'package:flutter/material.dart';
import 'package:lanweatherapp/components/current_weather.dart';
import 'package:lanweatherapp/components/day_overview.dart';
import 'package:lanweatherapp/components/hourly_overview.dart';
import 'package:lanweatherapp/models/time_frame.dart';
import 'package:lanweatherapp/services/weather_api.dart';
import 'package:lanweatherapp/strings.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    bool isMobile = MediaQuery.of(context).size.width < 600;

    return FutureBuilder(
      future: fetchAllWeatherData(),
      builder: (context, snapshot) {
        final Map<String, dynamic>? weatherData = snapshot.data;
        final daily = weatherData != null ? TimeFrame.fromJson(weatherData["nws"]["daily"]) : null;
        final today = daily?.periods[0];

        return isMobile
            ? ListView(
                padding: const EdgeInsets.symmetric(vertical: 40.0),
                children: [
                  Container(
                    margin: const EdgeInsets.fromLTRB(25.0, 15.0, 0.0, 0.0),
                    child: Text(Strings.appName, style: TextStyle(color: Colors.white, fontSize: 50)),
                  ),
                  CurrentWeather(weatherData: weatherData),
                  Column(
                    children: [
                      Container(
                        margin: const EdgeInsets.only(top: 30.0),
                        child: DayOverview(
                          title: Strings.today,
                          content: Strings.todayDetails,
                          weather: today?.shortForecast ?? "Unknown",
                          temperature: today?.temperature ?? 0,
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.only(top: 30.0),
                        child: HourlyOverview(weatherData: weatherData),
                      ),
                    ],
                  ),
                ],
              )
            : ListView(
                padding: const EdgeInsets.symmetric(vertical: 20.0),
                children: [
                  Container(
                    margin: const EdgeInsets.fromLTRB(25.0, 15.0, 0.0, 0.0),
                    child: Text(Strings.appName, style: TextStyle(color: Colors.white, fontSize: 50)),
                  ),
                  CurrentWeather(weatherData: weatherData),
                  Row(
                    children: [
                      Expanded(
                        child: Container(
                          margin: const EdgeInsets.only(top: 30.0),
                          child: DayOverview(
                            title: Strings.today,
                            content: Strings.todayDetails,
                            weather: today?.shortForecast ?? "Unknown",
                            temperature: today?.temperature ?? 0,
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          margin: const EdgeInsets.only(top: 30.0),
                          child: HourlyOverview(weatherData: weatherData),
                        ),
                      ),
                    ],
                  ),
                ],
              );
      },
    );
  }
}
