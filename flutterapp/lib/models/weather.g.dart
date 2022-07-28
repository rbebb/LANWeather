// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'weather.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Weather _$WeatherFromJson(Map<String, dynamic> json) => Weather(
      json['coverage'] as String?,
      json['weather'] as String?,
      json['intensity'] as String?,
      (json['visibility'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$WeatherToJson(Weather instance) => <String, dynamic>{
      'coverage': instance.coverage,
      'weather': instance.weather,
      'intensity': instance.intensity,
      'visibility': instance.visibility,
    };
