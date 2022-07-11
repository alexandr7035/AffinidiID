package by.alexandr7035.affinidi_id.presentation.scan_credential_qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.openAppSystemSettings
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScanCredentialQrCodeBinding
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.alexandr7035.affinidi_id.presentation.common.permissions.PermissionsCheckResult
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ScanCredentialQrCodeFragment : Fragment() {

    private val binding by viewBinding(FragmentScanCredentialQrCodeBinding::bind)
    private val viewModel by viewModels<ScanCredentialQrViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_credential_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Show QR scanner by default
        binding.permissionsErrorLayout.isVisible = false
        binding.qrCodeLayout.isVisible = true

        // Ask for camera permissions
        when (viewModel.askForCameraPermissions(requireActivity())) {
            PermissionsCheckResult.PERMISSION_GRANTED -> {
                // Start QR code scanning
                binding.qrCodeView.resume()
            }

            PermissionsCheckResult.SHOULD_REQUEST_PERMISSION -> {
                viewModel.requestCameraPermissions(requireActivity())
            }

            PermissionsCheckResult.SHOULD_REDIRECT_TO_SETTINGS -> {
                // Show error view
                binding.permissionsErrorLayout.isVisible = true
                binding.qrCodeLayout.isVisible = false
            }
        }

        // Disable text label on qrcode view
        binding.qrCodeView.setStatusText("")

        // On QR scanned
        binding.qrCodeView.decodeSingle { scanResult ->
            Timber.debug("barcode result $scanResult")
            binding.qrCodeView.pause()
            requireContext().vibrate(VibrationMode.SHORT)

            findNavController().navigateSafe(ScanCredentialQrCodeFragmentDirections.actionScanCredentialQrCodeFragmentToScannedCredentialFragment(
                scanResult.text
            ))
        }

        binding.settingsButton.setOnClickListener {
            requireContext().openAppSystemSettings()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.qrCodeView.pause()
    }


    // Chack permissions again when return from app system settings page
    override fun onResume() {
        super.onResume()

        // Ask for camera permissions
        when (viewModel.askForCameraPermissions(requireActivity())) {
            PermissionsCheckResult.PERMISSION_GRANTED -> {
                binding.permissionsErrorLayout.isVisible = false
                binding.qrCodeLayout.isVisible = true
                // Start QR code scanning
                binding.qrCodeView.resume()
            }

            PermissionsCheckResult.SHOULD_REQUEST_PERMISSION -> {
               // Don't ask in onResume as it's called every time
               // When permissions dialog dismisses
            }

            PermissionsCheckResult.SHOULD_REDIRECT_TO_SETTINGS -> {
                // Show error view
                binding.permissionsErrorLayout.isVisible = true
                binding.qrCodeLayout.isVisible = false
            }
        }
    }

}