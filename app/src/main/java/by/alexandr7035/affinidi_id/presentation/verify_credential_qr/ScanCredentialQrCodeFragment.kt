package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.alexandr7035.affinidi_id.R
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback

import com.journeyapps.barcodescanner.ScanContract

import com.journeyapps.barcodescanner.ScanOptions

import androidx.activity.result.ActivityResultLauncher
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScanCredentialQrCodeBinding
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.kirich1409.viewbindingdelegate.viewBinding
import com.journeyapps.barcodescanner.ScanIntentResult
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

        // Register the launcher and result handler
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(), "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        }

        binding.qrCodeView.decodeSingle { scanResult ->
            Timber.debug("barcode result $scanResult")
            requireContext().showToast("$scanResult")
            binding.qrCodeView.pause()
//            requireContext().vibrate(VibrationMode.SHORT)
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