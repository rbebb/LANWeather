import 'package:flutterapp/models/period.dart';
import 'package:json_annotation/json_annotation.dart';

part 'time_frame.g.dart';

@JsonSerializable()
class TimeFrame {
  final List<Period> periods;

  TimeFrame(this.periods);

  factory TimeFrame.fromJson(Map<String, dynamic> json) => _$TimeFrameFromJson(json);

  Map<String, dynamic> toJson() => _$TimeFrameToJson(this);
}
