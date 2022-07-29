import 'package:json_annotation/json_annotation.dart';

part 'sensor.g.dart';

@JsonSerializable()
class Sensor {
  final double temperature;
  final double? humidity;

  Sensor({this.temperature = 0.0, this.humidity});

  factory Sensor.fromJson(Map<String, dynamic> json) => _$SensorFromJson(json);

  Map<String, dynamic> toJson() => _$SensorToJson(this);
}
