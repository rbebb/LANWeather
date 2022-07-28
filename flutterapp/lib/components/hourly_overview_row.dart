import 'package:flutter/material.dart';

class HourlyOverviewRow extends StatelessWidget {
  final String time;
  final String imageLocation;
  final String temperature;

  const HourlyOverviewRow({
    required this.time,
    required this.imageLocation,
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
              imageLocation,
              height: 100.0,
              width: 100.0,
            ),
          ),
        ),
        Flexible(
          flex: 2,
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
