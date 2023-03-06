package by.alexandr7035.affinidi_id.core.extensions

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.presentation.registration.RegistrationFragmentDirections

fun Fragment.showErrorDialog(title: String, message: String) {
    findNavController().navigateSafe(
        RegistrationFragmentDirections
            .actionGlobalErrorFragment(title, message)
    )
}


fun Fragment.showBiometricPrompt(
    onSuccess: () -> Unit,
    onError: (error: String) -> Unit,
    title: String? = null
) {
    val ctx: Context

    try {
        ctx = this.requireContext()
    } catch (e: IllegalStateException) {
        return
    }

    val executor = ContextCompat.getMainExecutor(ctx)
    val biometricPrompt = BiometricPrompt(this, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError.invoke(getString(R.string.error_auth_failed_template, errString))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess.invoke()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError.invoke(getString(R.string.error_auth_failed))
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title ?: getString(R.string.lock_with_biometrics))
        .setNegativeButtonText(getString(R.string.cancel))
        .build()

    biometricPrompt.authenticate(promptInfo)
}