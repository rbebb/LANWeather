import 'package:flutter/material.dart';
import 'package:flutterapp/components/current_weather.dart';
import 'package:flutterapp/components/day_overview.dart';
import 'package:flutterapp/components/hourly_overview.dart';
import 'package:flutterapp/models/current.dart';
import 'package:flutterapp/services/weather_api.dart';
import 'package:flutterapp/strings.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // Future<Current> _futureCurrent;

  @override
  void initState() {
    super.initState();
    // _futureCurrent = fetchWeatherInfo();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: fetchAllWeatherData(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          final current = snapshot.data as Current;
          return ListView(
            padding: const EdgeInsets.symmetric(vertical: 40.0),
            children: [
              Container(
                margin: const EdgeInsets.fromLTRB(25.0, 15.0, 0.0, 0.0),
                child: Text(
                  Strings.appName,
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 50,
                  ),
                ),
              ),
              CurrentWeather(current: current),
              Container(
                margin: const EdgeInsets.only(top: 30.0),
                child: DayOverview(
                  title: Strings.today,
                  content: Strings.todayDetails,
                  weather: "Sunny",
                  temperature: 70.0,
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 30.0),
                child: HourlyOverview(),
              )
            ],
          );
        } else {
          return Container();
        }
      },
    );
  }
}
