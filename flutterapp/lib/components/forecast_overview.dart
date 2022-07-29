import 'package:flutter/material.dart';
import 'package:flutterapp/components/forecast_overview_row.dart';
import 'package:flutterapp/models/time_frame.dart';
import 'package:flutterapp/strings.dart';

class ForecastOverview extends StatelessWidget {
  final Map<String, dynamic> weatherData;

  const ForecastOverview({required this.weatherData});

  @override
  Widget build(BuildContext context) {
    final daily = TimeFrame.fromJson(weatherData["nws"]["daily"]);
    final periodsOnlyDayTime = daily.periods
        .where((period) => period.isDaytime != null && period.isDaytime!)
        .toList()
        .take(7) // Get the first 7 entries in the list
        .toList();

    return Container(
      color: Colors.white,
      height: 500.0,
      padding: const EdgeInsets.symmetric(horizontal: 5.0),
      margin: const EdgeInsets.fromLTRB(20.0, 0.0, 20.0, 10.0),
      child: Column(
        children: [
          Flexible(
            flex: 1,
            child: Center(
              child: Text(
                Strings.sevenDayForecast,
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 24,
                ),
              ),
            ),
          ),
          ...buildRows(
            periodsOnlyDayTime.map((period) => period.name ?? "").toList(),
            [
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
            ],
            periodsOnlyDayTime.map((period) => "${period.temperature}º").toList(),
          ),
        ],
      ),
    );
  }
}

List<Widget> buildRows(
  final List<String> days,
  final List<String> imageLocations,
  final List<String> temperatures,
) {
  return [
    // Unpack each row's group (List) of widgets with spread operator
    for (int i = 0; i < 7; i++) ...[
      Divider(
        thickness: 3.0,
      ),
      Flexible(
        flex: 1,
        child: ForecastOverviewRow(
          day: days[i],
          imageLocation: imageLocations[i],
          temperature: temperatures[i],
        ),
      )
    ]
  ];
}
