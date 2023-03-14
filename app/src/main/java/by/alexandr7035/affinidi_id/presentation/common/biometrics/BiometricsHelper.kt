package by.alexandr7035.affinidi_id.presentation.common.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*


class BiometricsHelper {
    fun checkIfBiometricsAvailable(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL or BIOMETRIC_WEAK)
    }
}