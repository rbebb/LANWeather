import 'package:json_annotation/json_annotation.dart';

part 'period.g.dart';

@JsonSerializable()
class Period {
  final String startTime;
  final int? number;
  final String? name;
  final String? endTime;
  final bool? isDaytime;
  final int? temperature;
  final String? temperatureUnit; // this was a Char in Kotlin
  final String? temperatureTrend;
  final String? windSpeed;
  final String? windDirection;
  final String? icon;
  final String? shortForecast;
  final String? detailedForecast;

  Period(
    this.startTime,
    this.number,
    this.name,
    this.endTime,
    this.isDaytime,
    this.temperature,
    this.temperatureUnit,
    this.temperatureTrend,
    this.windSpeed,
    this.windDirection,
    this.icon,
    this.shortForecast,
    this.detailedForecast,
  );

  factory Period.fromJson(Map<String, dynamic> json) => _$PeriodFromJson(json);

  Map<String, dynamic> toJson() => _$PeriodToJson(this);
}
