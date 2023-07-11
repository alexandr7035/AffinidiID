package by.alexandr7035.affinidi_id.presentation.common.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*


object BiometricsHelper {
    fun checkIfBiometricsAvailable(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
    }
}