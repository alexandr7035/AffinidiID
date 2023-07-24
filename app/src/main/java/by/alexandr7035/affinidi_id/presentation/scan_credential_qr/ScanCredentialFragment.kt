package by.alexandr7035.affinidi_id.presentation.scan_credential_qr

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.setNavigationResult
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScanCredentialBinding
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber


class ScanCredentialFragment : BottomSheetDialogFragment(R.layout.fragment_scan_credential) {

    private val binding by viewBinding(FragmentScanCredentialBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetDialog = dialog as BottomSheetDialog
        val behavior = bottomSheetDialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true

        setupQrView()
    }

    private fun setupQrView() {
        binding.qr.statusView.isVisible = false

        binding.qr.viewFinder.setLaserVisibility(false)
        binding.qr.viewFinder.setMaskColor(
            ContextCompat.getColor(
                requireContext(), R.color.black_transp_80
            )
        )

        // On QR scanned
        binding.qr.decodeSingle { scanResult ->
            Timber.debug("barcode result $scanResult")
            binding.qr.pause()
            requireContext().vibrate(VibrationMode.SHORT)

            setNavigationResult<String>("qrCode", scanResult.text)
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.qr.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.qr.pause()
    }
}