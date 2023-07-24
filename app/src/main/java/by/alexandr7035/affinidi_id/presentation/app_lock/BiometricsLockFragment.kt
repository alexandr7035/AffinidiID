package by.alexandr7035.affinidi_id.presentation.app_lock

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showBiometricPrompt
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.databinding.FragmentBiometricsLockBinding
import by.alexandr7035.affinidi_id.presentation.MainViewModel
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar


class BiometricsLockFragment : Fragment(R.layout.fragment_biometrics_lock) {

    private val viewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(FragmentBiometricsLockBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't allow going back until app is locked
        // Minimize the app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().moveTaskToBack(true)
            }
        })

        binding.unlockBtn.setOnClickListener {
            showUnlockPrompt()
        }

        binding.logout.setOnClickListener {
            // TODO confirmation dialog
            viewModel.logOut()
        }

        viewModel.getLogOutObservable().observe(viewLifecycleOwner) {
            findNavController().navigateSafe(
                BiometricsLockFragmentDirections.actionBiometricsLockFragmentToLoginFragment()
            )
        }

        showUnlockPrompt()
    }


    private fun showUnlockPrompt() {
        showBiometricPrompt(
            onSuccess = {
                findNavController().popBackStack()
            },
            onError = {
                binding.root.showSnackBar(
                    message = it, snackBarMode = SnackBarMode.Negative, snackBarLength = Snackbar.LENGTH_SHORT
                )
            },
        )
    }
}