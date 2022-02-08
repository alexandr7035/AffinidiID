package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScanCredentialQrCodeBinding
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber


class ScanCredentialQrCodeFragment : Fragment() {

    private val binding by viewBinding(FragmentScanCredentialQrCodeBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_credential_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.qrCodeView.setStatusText("")

        binding.qrCodeView.decodeSingle { scanResult ->
            Timber.debug("barcode result $scanResult")
            binding.qrCodeView.pause()
            requireContext().vibrate(VibrationMode.SHORT)

            findNavController().navigateSafe(ScanCredentialQrCodeFragmentDirections.actionScanCredentialQrCodeFragmentToScannedCredentialFragment(
                scanResult.text
            ))
        }
    }

    override fun onPause() {
        super.onPause()
        binding.qrCodeView.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.qrCodeView.resume()
    }

}