import 'package:flutter/material.dart';
import 'package:flutterapp/components/forecast_overview_row.dart';
import 'package:flutterapp/models/time_frame.dart';
import 'package:flutterapp/strings.dart';

class ForecastOverview extends StatelessWidget {
  final Map<String, dynamic>? weatherData;

  const ForecastOverview({required this.weatherData});

  @override
  Widget build(BuildContext context) {
    final daily = weatherData != null ? TimeFrame.fromJson(weatherData!["nws"]["daily"]) : null;
    final periodsOnlyDayTime = daily?.periods
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
            periodsOnlyDayTime?.map((period) => period.name ?? "").toList(),
            periodsOnlyDayTime?.map((period) => period.shortForecast).toList(),
            periodsOnlyDayTime?.map((period) => "${period.temperature}").toList(),
          ),
        ],
      ),
    );
  }
}

List<Widget> buildRows(
  final List<String>? days,
  final List<String?>? shortForecast,
  final List<String>? temperatures,
) {
  final List<Widget> rows = [];
  // Unpack each row's group (List) of widgets with spread operator
  for (int i = 0; i < 7; i++) {
    final tempFahrenheit = (double.parse(temperatures?[i] ?? "-1") * 1.8) + 32;
    rows.addAll([
      Divider(
        thickness: 3.0,
      ),
      Flexible(
        flex: 1,
        child: ForecastOverviewRow(
          day: days?[i] ?? "Monday",
          shortForecast: shortForecast?[i] ?? "Clear",
          temperature: "${tempFahrenheit.round()}ºF",
        ),
      )
    ]);
  }
  return rows;
}
