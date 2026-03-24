import 'package:flutter/material.dart';
import 'package:lanweatherapp/models/current.dart';
import 'package:lanweatherapp/strings.dart';
import 'package:sprintf/sprintf.dart';

class CurrentWeather extends StatelessWidget {
  final Map<String, dynamic>? weatherData;

  const CurrentWeather({required this.weatherData});

  @override
  Widget build(BuildContext context) {
    final current = weatherData != null ? Current.fromJson(weatherData!["nws"]["current"]) : null;
    final double currentTempFahrenheit = current?.temperature != null ? (current!.temperature! * 1.8) + 32 : -1.0;

    return Row(
      children: [
        Flexible(
          flex: 1,
          fit: FlexFit.tight,
          child: Image.asset('assets/weather_icons/ic_sun.png', height: 100.0, width: 100.0),
        ),
        Flexible(
          flex: 2,
          fit: FlexFit.tight,
          child: Wrap(
            children: [
              Text(
                sprintf(Strings.currentWeather, [
                  current?.relativeHumidity ?? -1,
                  current?.windSpeed ?? -1,
                  currentTempFahrenheit,
                ]),
                style: TextStyle(color: Colors.white, fontSize: 28),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
