package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.databinding.FragmentScannedCredentialBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannedCredentialFragment : Fragment() {

    private val viewModel by viewModels<VerifyCredentialQrViewModel>()
    private val safeArgs by navArgs<ScannedCredentialFragmentArgs>()
    private val binding by viewBinding(FragmentScannedCredentialBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanned_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCredentialLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is ObtainVcFromQrCodeResModel.Success -> {
                    // TODO ui
                    requireContext().showToast(it.credential.rawVCData)
                }

                is ObtainVcFromQrCodeResModel.Fail -> {
                    requireContext().showToast("error ${it.errorType.name}")
                }
            }
        })

        viewModel.obtainCredential(safeArgs.qrLink)
    }
}