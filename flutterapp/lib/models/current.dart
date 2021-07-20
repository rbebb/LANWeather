import 'package:flutterapp/models/weather.dart';
import 'package:json_annotation/json_annotation.dart';

part 'current.g.dart';

@JsonSerializable()
class Current {
  final double? temperature;
  final double? maxTemperature;
  final double? minTemperature;
  final int? relativeHumidity;
  final double? windChill;
  final double? windDirection;
  final double? windSpeed;
  final double? windGust;
  final Weather? weather;
  final double? probabilityOfPrecipitation;

  Current(
    this.temperature,
    this.maxTemperature,
    this.minTemperature,
    this.relativeHumidity,
    this.windChill,
    this.windDirection,
    this.windSpeed,
    this.windGust,
    this.weather,
    this.probabilityOfPrecipitation,
  );

  factory Current.fromJson(Map<String, dynamic> json) =>
      _$CurrentFromJson(json);

  Map<String, dynamic> toJson() => _$CurrentToJson(this);
}
