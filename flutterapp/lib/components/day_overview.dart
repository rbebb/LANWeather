import 'package:flutter/material.dart';
import 'package:sprintf/sprintf.dart';

class DayOverview extends StatelessWidget {
  final String title;
  final String content;
  final String weather;
  final double temperature;

  const DayOverview({
    required this.title,
    required this.content,
    required this.weather,
    required this.temperature,
  });

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
                title,
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
            flex: 3,
            child: Row(
              children: [
                Flexible(
                  flex: 1,
                  child: Center(
                    child: Image.asset(
                      'assets/weather_icons/ic_sun.png',
                      height: 100.0,
                      width: 100.0,
                    ),
                  ),
                ),
                Flexible(
                  flex: 3,
                  child: Center(
                    child: Text(
                      sprintf(content, [weather, temperature]),
                      style: TextStyle(
                        color: Colors.grey,
                        fontSize: 24,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
