import 'dart:async';

import 'package:flutter/services.dart';

class FlutterVibrator {
  static const MethodChannel _channel = const MethodChannel('flutter_vibrator');

  static Future<void> vibrate({int duration = 500}) async {
     _channel.invokeMethod('vibrate', {"duration": duration});
  }
  static Future<void> vibrateWaveForm({ List<int> timing = const [400,500], int repeat = 1}) async {
    _channel.invokeMethod('vibrateWaveForm', {"timing": timing, "repeat":repeat});
  }

  static Future<bool> hasVibrator() async {
    return await _channel.invokeMethod('hasVibrator');
  }
  static Future<bool> cancelVibrator() async {
    return await _channel.invokeMethod('cancelVibrator');
  }

  static Future<bool> hasAmplitudeControl() async {
    return await _channel.invokeMethod('hasAmplitudeControl');
  }
}
