package by.alexandr7035.affinidi_id.presentation.main_menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.BuildConfig
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.*
import by.alexandr7035.affinidi_id.databinding.FragmentMainMenuBinding
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.Executor

@AndroidEntryPoint
class MainMenuFragment : Fragment() {

    private val binding by viewBinding(FragmentMainMenuBinding::bind)
    private val viewModel by viewModels<MainMenuViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItems = listOf(
            MenuItemModel(
                title = getString(R.string.change_password),
                clickListener = {
                    findNavController().navigateSafe(
                        MainMenuFragmentDirections
                            .actionMainMenuFragmentToChangePasswordFragment()
                    )
                })
        )

        val layoutManager = LinearLayoutManager(requireContext())

        // Decoration (spacing) for menu items
        val decoration = DividerItemDecoration(
            binding.recycler.context,
            layoutManager.orientation
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.primary_menu_item_decoration)?.let {
            decoration.setDrawable(it)
        }
        binding.recycler.addItemDecoration(decoration)

        val adapter = MainProfileMenuAdapter(menuItems)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager

        // Set app version
        binding.appVersionView.text = getString(
            R.string.app_name_with_version,
            BuildConfig.VERSION_NAME
        )

        viewModel.init()
        viewModel.userProfileLiveData.observe(viewLifecycleOwner) { profile ->

            Timber.debug(profile.imageUrl)

            binding.profileImageView.load(
                data = profile.imageUrl,
                imageLoader = requireContext().svgLoader()
            )
        }

        binding.toolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.logoutItem -> {
                    findNavController().navigateSafe(MainMenuFragmentDirections.actionMainMenuFragmentToLogoutFragment())
                }
            }

            true
        }

        binding.biometricsSwitch.setOnClickListener {
            if (binding.biometricsSwitch.isChecked) {
                showPrompt(requireContext(),
                    onSuccess = {
                        viewModel.setAppLockedWithBiometrics(true)
                        binding.biometricsSwitch.isChecked = true
                        binding.root.showSnackBar(getString(R.string.app_is_now_locked_with_biometrics), SnackBarMode.Positive, snackBarLength = Snackbar.LENGTH_SHORT)
                    },
                    onError = {
                        binding.biometricsSwitch.isChecked = false
                        binding.root.showSnackBar(it, SnackBarMode.Negative, snackBarLength = Snackbar.LENGTH_SHORT)
                    })
            } else {
                binding.root.showSnackBar(getString(R.string.biometric_lock_disabled), SnackBarMode.Neutral, snackBarLength = Snackbar.LENGTH_SHORT)
                viewModel.setAppLockedWithBiometrics(false)
            }
        }

        viewModel.biometricsAvailableStatusLiveData.observe(viewLifecycleOwner) { biometricsStatus ->
            when (biometricsStatus) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    enableBiometricsSwitch()
                    binding.biometricsSwitch.isChecked = viewModel.checkAppLockedWithBiometrics()
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    disableBiometricsSwitch()
                    binding.biometricError.text = getString(R.string.biometrics_not_enabled)
                }
                else -> {
                    disableBiometricsSwitch()
                    binding.biometricError.text = getString(R.string.biometrics_not_available)
                }
            }
        }

        viewModel.checkBiometricAvailability(requireContext())
    }


    private fun showPrompt(context: Context, onSuccess: () -> Unit, onError: (error: String) -> Unit) {
        val executor = ContextCompat.getMainExecutor(context)
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
            .setTitle(getString(R.string.lock_with_biometrics))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }


    private fun disableBiometricsSwitch() {
        binding.biometricsSwitch.isEnabled = false
        binding.biometricsSwitch.isChecked = false
        binding.biometricError.isVisible = true
    }

    private fun enableBiometricsSwitch() {
        binding.biometricsSwitch.isEnabled = true
        binding.biometricError.isVisible = false
    }
}