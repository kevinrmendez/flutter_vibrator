package kevinrmendez.flutter_vibrator

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

import android.content.Context;
import android.os.Vibrator
import android.os.VibrationEffect

class FlutterVibratorPlugin(val context: Context): MethodCallHandler {
  val vibe:Vibrator
  var effect:VibrationEffect? = null
  init {
     vibe = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
  }

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_vibrator")
      channel.setMethodCallHandler(FlutterVibratorPlugin(registrar.context()))
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when(call.method){
      "vibrate"-> {
        val durationInt: Int? = call.argument("duration")
        if(durationInt != null){
          val durationLong: Long = durationInt.toLong()
          effect = VibrationEffect.createOneShot(durationLong, VibrationEffect.DEFAULT_AMPLITUDE);
        }else{
          effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
        }

        vibe.vibrate(effect)
//      result.success("Android ${android.os.Build.VERSION.RELEASE}")
      }
      "vibrateWaveForm"-> {
        val timing: ArrayList<Int>? = call.argument("timing")
        if(timing != null){
          val timingLong = timing.map{ it.toLong() }
          val timingLongArray = timingLong.toLongArray()
          val repeat: Int? = call.argument("repeat")
          if(repeat != null ){
            effect = VibrationEffect.createWaveform(timingLongArray, repeat);
          }
        }else {
          val timing: LongArray = longArrayOf(400,500)
          effect = VibrationEffect.createWaveform(timing, 1);
        }
        vibe.vibrate(effect)
//      result.success("Android ${android.os.Build.VERSION.RELEASE}")
      }
      "hasVibrator"-> {
      var hasVibrator = vibe.hasVibrator()
      result.success(hasVibrator)
    }
      "hasAmplitudeControl"-> {
        var hasAmplitudeControl = vibe.hasAmplitudeControl()
        result.success(hasAmplitudeControl)
      }
      "cancelVibrator"-> {
         vibe.cancel()
      }
    }
//    if (call.method == "getPlatformVersion") {
////
//      vibe.vibrate(500)
//      vibe.vibrate(effect)
////      result.success("Android ${android.os.Build.VERSION.RELEASE}")
//
//    } else {
//      result.notImplemented()
//    }
  }
}
