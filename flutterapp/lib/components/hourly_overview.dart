import 'package:flutter/material.dart';
import 'package:flutterapp/components/hourly_overview_row.dart';
import 'package:flutterapp/models/time_frame.dart';
import 'package:flutterapp/strings.dart';
import 'package:intl/intl.dart';

class HourlyOverview extends StatelessWidget {
  final Map<String, dynamic>? weatherData;

  const HourlyOverview({required this.weatherData});

  @override
  Widget build(BuildContext context) {
    final hourly = weatherData != null ? TimeFrame.fromJson(weatherData!["nws"]["hourly"]) : null;
    final periodsOnlyDayTime = hourly?.periods
        .where((period) => period.isDaytime != null && period.isDaytime!)
        .toList()
        .take(3) // Get the first 3 entries in the list
        .toList();

    return Container(
      color: Colors.white,
      height: 300.0,
      padding: const EdgeInsets.symmetric(horizontal: 5.0),
      margin: const EdgeInsets.symmetric(horizontal: 20.0),
      child: Column(
        children: [
          Flexible(
            flex: 1,
            child: Center(
              child: Text(
                Strings.hourly,
                style: TextStyle(
                  color: Colors.grey,
                  fontSize: 24,
                ),
              ),
            ),
          ),
          ...buildRows(
            periodsOnlyDayTime?.map((period) => period.startTime).toList(),
            periodsOnlyDayTime?.map((period) => period.shortForecast).toList(),
            periodsOnlyDayTime?.map((period) => "${period.temperature}").toList(),
          ),
        ],
      ),
    );
  }
}

List<Widget> buildRows(
  final List<String>? hours,
  final List<String?>? shortForecast,
  final List<String>? temperatures,
) {
  final List<Widget> rows = [];
  // Unpack each row's group (List) of widgets with spread operator
  for (int i = 0; i < 3; i++) {
    final tempFahrenheit = (double.parse(temperatures?[i] ?? "-1") * 1.8) + 32;
    final DateTime? time = hours?[i] != null ? DateTime.parse(hours![i]) : null;
    final String? timeFormatted = time != null ? DateFormat.jm().format(time) : null;

    rows.addAll([
      Divider(
        thickness: 3.0,
      ),
      Flexible(
        flex: 1,
        child: HourlyOverviewRow(
          time: timeFormatted ?? "1:00 PM",
          shortForecast: shortForecast?[i] ?? "Clear",
          temperature: "${tempFahrenheit.round()}ºF",
        ),
      )
    ]);
  }
  return rows;
}
