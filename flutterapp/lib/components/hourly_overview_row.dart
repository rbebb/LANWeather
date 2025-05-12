import 'package:flutter/material.dart';
import 'package:flutterapp/utils/ImageUtils.dart';

class HourlyOverviewRow extends StatelessWidget {
  final String time;
  final String shortForecast;
  final String temperature;

  const HourlyOverviewRow({
    required this.time,
    required this.shortForecast,
    required this.temperature,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Flexible(
          flex: 1,
          child: Center(
            child: Text(
              time,
              style: TextStyle(
                color: Colors.grey,
                fontSize: 24,
              ),
            ),
          ),
        ),
        Flexible(
          flex: 1,
          child: Center(
            child: Image.asset(
              ImageUtils.getWeatherIconAsset(shortForecast) ?? "assets/weather_icons/ic_sun.png",
              height: 100.0,
              width: 100.0,
            ),
          ),
        ),
        Flexible(
          flex: 1,
          child: Center(
            child: Text(
              temperature,
              style: TextStyle(
                color: Colors.grey,
                fontSize: 24,
              ),
            ),
          ),
        ),
      ],
    );
  }
}
