package by.alexandr7035.affinidi_id.presentation.verify_credential

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentScanCredentialBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


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
                requireContext(),
                R.color.black_transp_80
            )
        )

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