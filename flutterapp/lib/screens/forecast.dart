import 'package:flutter/material.dart';
import 'package:flutterapp/components/day_overview.dart';
import 'package:flutterapp/components/forecast_overview.dart';
import 'package:flutterapp/strings.dart';

class Forecast extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.symmetric(vertical: 40.0),
      children: [
        Container(
          margin: const EdgeInsets.only(top: 18.0),
          alignment: Alignment.center,
          child: Text(
            Strings.forecast,
            style: TextStyle(
              color: Colors.white,
              fontSize: 40,
            ),
          ),
        ),
        Container(
          margin: const EdgeInsets.only(top: 25.0),
          child: DayOverview(
            title: Strings.tomorrow,
            content: Strings.tomorrowDetails,
            weather: "Sunny",
            temperature: 68.0,
          ),
        ),
        Container(
          margin: const EdgeInsets.only(top: 25.0),
          child: ForecastOverview(),
        )
      ],
    );
  }
}
