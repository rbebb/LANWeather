import 'package:flutter/material.dart';
import 'package:flutterapp/components/hourly_overview_row.dart';
import 'package:flutterapp/strings.dart';

class HourlyOverview extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
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
          Divider(
            thickness: 3.0,
          ),
          Flexible(
            flex: 1,
            child: HourlyOverviewRow(
              time: "1:00PM",
              imageLocation: "assets/weather_icons/ic_sun.png",
              temperature: "70º",
            ),
          ),
          Divider(
            thickness: 3.0,
          ),
          Flexible(
            flex: 1,
            child: HourlyOverviewRow(
              time: "1:00PM",
              imageLocation: "assets/weather_icons/ic_sun.png",
              temperature: "70º",
            ),
          ),
          Divider(
            thickness: 3.0,
          ),
          Flexible(
            flex: 1,
            child: HourlyOverviewRow(
              time: "1:00PM",
              imageLocation: "assets/weather_icons/ic_sun.png",
              temperature: "70º",
            ),
          ),
        ],
      ),
    );
  }
}
