package by.alexandr7035.affinidi_id.core.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.presentation.registration.RegistrationFragmentDirections

fun Fragment.showErrorDialog(title: String, message: String) {
    findNavController().navigateSafe(
        RegistrationFragmentDirections
            .actionGlobalErrorFragment(title, message))
}