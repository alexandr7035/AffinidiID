package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.alexandr7035.affinidi_id.R

class ScanCredentialQrCodeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_credential_qr_code, container, false)
    }


}