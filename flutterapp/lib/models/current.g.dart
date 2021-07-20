// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'current.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Current _$CurrentFromJson(Map<String, dynamic> json) {
  return Current(
    (json['temperature'] as num?)?.toDouble(),
    (json['maxTemperature'] as num?)?.toDouble(),
    (json['minTemperature'] as num?)?.toDouble(),
    json['relativeHumidity'] as int?,
    (json['windChill'] as num?)?.toDouble(),
    (json['windDirection'] as num?)?.toDouble(),
    (json['windSpeed'] as num?)?.toDouble(),
    (json['windGust'] as num?)?.toDouble(),
    json['weather'] == null
        ? null
        : Weather.fromJson(json['weather'] as Map<String, dynamic>),
    (json['probabilityOfPrecipitation'] as num?)?.toDouble(),
  );
}

Map<String, dynamic> _$CurrentToJson(Current instance) => <String, dynamic>{
      'temperature': instance.temperature,
      'maxTemperature': instance.maxTemperature,
      'minTemperature': instance.minTemperature,
      'relativeHumidity': instance.relativeHumidity,
      'windChill': instance.windChill,
      'windDirection': instance.windDirection,
      'windSpeed': instance.windSpeed,
      'windGust': instance.windGust,
      'weather': instance.weather,
      'probabilityOfPrecipitation': instance.probabilityOfPrecipitation,
    };
