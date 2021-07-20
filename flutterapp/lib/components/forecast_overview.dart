import 'package:flutter/material.dart';
import 'package:flutterapp/components/forecast_overview_row.dart';
import 'package:flutterapp/strings.dart';

class ForecastOverview extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
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
            [
              "Monday",
              "Tuesday",
              "Wednesday",
              "Thursday",
              "Friday",
              "Saturday",
              "Sunday",
            ],
            [
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
              "assets/weather_icons/ic_sun.png",
            ],
            [
              "70º",
              "70º",
              "70º",
              "70º",
              "70º",
              "70º",
              "70º",
            ],
          ),
        ],
      ),
    );
  }
}

List<Widget> buildRows(
  List<String> days,
  List<String> imageLocations,
  List<String> temperatures,
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
