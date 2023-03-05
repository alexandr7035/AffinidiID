package by.alexandr7035.affinidi_id.presentation.main_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.BuildConfig
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.core.extensions.svgLoader
import by.alexandr7035.affinidi_id.databinding.FragmentMainMenuBinding
import by.alexandr7035.affinidi_id.presentation.common.biometrics.BiometricsHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

        binding.biometricsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requireContext().showToast("Enable biometrics", Toast.LENGTH_SHORT)
//                viewModel.checkBiometricAvailability(requireContext())
            }
            else {
                requireContext().showToast("Disable biometrics", Toast.LENGTH_SHORT)
            }
        }

        viewModel.biometricsAvailableStatusLiveData.observe(viewLifecycleOwner) { biometricsStatus ->

            fun disableBiometricsSwitch() {
                binding.biometricsSwitch.isEnabled = false
                binding.biometricsSwitch.isChecked = false
                binding.biometricError.isVisible = true
            }

            when (biometricsStatus ) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    requireContext().showToast( "App can authenticate using biometrics.")
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    disableBiometricsSwitch()
                    binding.biometricError.text = getString(R.string.biometrics_not_available)
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    disableBiometricsSwitch()
                    binding.biometricError.text = getString(R.string.biometrics_not_available)
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    disableBiometricsSwitch()
                    binding.biometricError.text = getString(R.string.biometrics_not_enabled)
                }
            }
        }

        viewModel.checkBiometricAvailability(requireContext())
    }
}