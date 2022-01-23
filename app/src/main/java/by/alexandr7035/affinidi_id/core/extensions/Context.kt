package by.alexandr7035.affinidi_id.core.extensions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}


fun Context.vibrate(vibrationTimeMills: Long) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
    } else {
        this.getSystemService(Context.VIBRATOR_SERVICE)
    } as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(vibrationTimeMills, VibrationEffect.DEFAULT_AMPLITUDE))
    }
    else {
        vibrator.vibrate(vibrationTimeMills)
    }
}