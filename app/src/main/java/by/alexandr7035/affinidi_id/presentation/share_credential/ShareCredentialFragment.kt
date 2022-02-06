package by.alexandr7035.affinidi_id.presentation.share_credential

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.databinding.FragmentShareCredentialBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShareCredentialFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentShareCredentialBinding::bind)
    private val viewModel by viewModels<ShareCredentialViewModel>()
    private val safeArgs by navArgs<ShareCredentialFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressView.root.isVisible = true

        viewModel.getShareCredentialLiveData().observe(viewLifecycleOwner, {
            binding.progressView.root.isVisible = false

            when (it) {
                is ShareCredentialResModel.Success -> {
                    // Base64 string to image
                    val decodedString: ByteArray = Base64.decode(it.base64QrCode, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    binding.qrCodeView.setImageBitmap(decodedByte)
                }
                is ShareCredentialResModel.Fail -> {
                    when (it.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }
                        else -> {
                            showErrorDialog(
                                getString(R.string.error_unknown_title),
                                getString(R.string.error_unknown)
                            )
                        }
                    }
                }
            }

        })

        viewModel.load(safeArgs.credentialId)
    }
}