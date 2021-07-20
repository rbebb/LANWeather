import 'package:flutter/material.dart';
import 'package:flutterapp/models/current.dart';
import 'package:flutterapp/strings.dart';
import 'package:sprintf/sprintf.dart';

class CurrentWeather extends StatelessWidget {
  final Current current;

  CurrentWeather({required this.current});

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Flexible(
          flex: 1,
          fit: FlexFit.tight,
          child: Image.asset(
            'assets/weather_icons/ic_sun.png',
            height: 100.0,
            width: 100.0,
          ),
        ),
        Flexible(
          flex: 2,
          fit: FlexFit.tight,
          child: Wrap(
            children: [
              Text(
                sprintf(Strings.currentWeather, [
                  current.relativeHumidity,
                  current.windSpeed,
                  current.temperature
                ]),
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 28,
                ),
              ),
            ],
          ),
        )
      ],
    );
  }
}
