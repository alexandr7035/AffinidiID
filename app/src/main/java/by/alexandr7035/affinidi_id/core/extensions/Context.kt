package by.alexandr7035.affinidi_id.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.widget.Toast
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import coil.ImageLoader
import coil.decode.SvgDecoder

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.vibrate(vibrationMode: VibrationMode) {
    // Time mills for modes
    val vibrationTimeMills = when (vibrationMode) {
        VibrationMode.SHORT -> 200
        VibrationMode.MEDIUM -> 500
        VibrationMode.LONG -> 1000
    }.toLong()

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(vibrationTimeMills, VibrationEffect.DEFAULT_AMPLITUDE))
    }
    else {
        vibrator.vibrate(vibrationTimeMills)
    }
}

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}


fun Context.svgLoader(): ImageLoader {
    val imageLoader = ImageLoader.Builder(this)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    return imageLoader
}