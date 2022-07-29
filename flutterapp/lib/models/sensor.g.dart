// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'sensor.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Sensor _$SensorFromJson(Map<String, dynamic> json) => Sensor(
      temperature: (json['temperature'] as num?)?.toDouble() ?? 0.0,
      humidity: (json['humidity'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$SensorToJson(Sensor instance) => <String, dynamic>{
      'temperature': instance.temperature,
      'humidity': instance.humidity,
    };
