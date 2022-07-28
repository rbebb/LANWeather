// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'period.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Period _$PeriodFromJson(Map<String, dynamic> json) => Period(
      json['startTime'] as String,
      json['number'] as int?,
      json['name'] as String?,
      json['endTime'] as String?,
      json['isDaytime'] as bool?,
      json['temperature'] as int?,
      json['temperatureUnit'] as String?,
      json['temperatureTrend'] as String?,
      json['windSpeed'] as String?,
      json['windDirection'] as String?,
      json['icon'] as String?,
      json['shortForecast'] as String?,
      json['detailedForecast'] as String?,
    );

Map<String, dynamic> _$PeriodToJson(Period instance) => <String, dynamic>{
      'startTime': instance.startTime,
      'number': instance.number,
      'name': instance.name,
      'endTime': instance.endTime,
      'isDaytime': instance.isDaytime,
      'temperature': instance.temperature,
      'temperatureUnit': instance.temperatureUnit,
      'temperatureTrend': instance.temperatureTrend,
      'windSpeed': instance.windSpeed,
      'windDirection': instance.windDirection,
      'icon': instance.icon,
      'shortForecast': instance.shortForecast,
      'detailedForecast': instance.detailedForecast,
    };
