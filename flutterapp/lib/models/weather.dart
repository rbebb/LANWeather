import 'package:json_annotation/json_annotation.dart';

part 'weather.g.dart';

@JsonSerializable()
class Weather {
  final String? coverage;
  final String? weather;
  final String? intensity;
  final double? visibility;

  Weather(
    this.coverage,
    this.weather,
    this.intensity,
    this.visibility,
  );

  factory Weather.fromJson(Map<String, dynamic> json) =>
      _$WeatherFromJson(json);

  Map<String, dynamic> toJson() => _$WeatherToJson(this);
}
