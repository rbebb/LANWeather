// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'time_frame.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TimeFrame _$TimeFrameFromJson(Map<String, dynamic> json) => TimeFrame(
      (json['periods'] as List<dynamic>)
          .map((e) => Period.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$TimeFrameToJson(TimeFrame instance) => <String, dynamic>{
      'periods': instance.periods,
    };
