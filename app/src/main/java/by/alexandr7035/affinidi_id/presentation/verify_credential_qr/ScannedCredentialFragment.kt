package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScannedCredentialBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDetailsUiModel
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationModelUi
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val credentialSubjectAdapter = CredentialDataAdapter()
        binding.credentialSubjectRecycler.adapter = credentialSubjectAdapter
        binding.credentialSubjectRecycler.layoutManager = LinearLayoutManager(requireContext())

        val metadataAdapter = CredentialDataAdapter()
        binding.metadataRecycler.adapter = metadataAdapter
        binding.metadataRecycler.layoutManager = LinearLayoutManager(requireContext())

        val proofAdapter = CredentialDataAdapter()
        binding.proofRecycler.adapter = proofAdapter
        binding.proofRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCredentialLiveData().observe(viewLifecycleOwner) { credentialData ->
            binding.progressView.root.isVisible = false

            when (credentialData) {
                is CredentialDetailsUiModel.Success -> {
                    binding.dataContainer.isVisible = true

                    // Set fields to cards
                    credentialSubjectAdapter.setItems(credentialData.credentialSubjectItems)
                    metadataAdapter.setItems(credentialData.metadataItems)
                    proofAdapter.setItems(credentialData.proofItems)

                    binding.credentialType.text = credentialData.credentialType
                    binding.statusMark.setColorFilter(credentialData.credentialStatus.statusColor)
                    binding.statusLabel.text = credentialData.credentialStatus.status

                    binding.verifyBtn.setOnClickListener {
                        binding.progressView.root.isVisible = true
                        viewModel.verifyCredential(credentialData.rawVcDataPrettyFormatted)
                    }
                }

                is CredentialDetailsUiModel.Fail -> {
                    binding.root.showSnackBar(
                        getString(R.string.credential_scan_error),
                        SnackBarMode.Negative,
                        Snackbar.LENGTH_LONG
                    )
                    findNavController().navigateUp()
                }
            }
        }

        viewModel.getVerificationLiveData().observe(viewLifecycleOwner) { verificationResult ->
            binding.progressView.root.isVisible = false

            when (verificationResult) {
                is VerificationModelUi.Success -> {

                    binding.root.showSnackBar(
                        message = verificationResult.messageText,
                        snackBarLength = Snackbar.LENGTH_LONG,
                        snackBarMode = verificationResult.validationResultSnackBarMode
                    )

                    requireContext().vibrate(VibrationMode.SHORT)
                }

                is VerificationModelUi.Fail -> {
                    when (verificationResult.errorType) {
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
        }

        // Load credential from QR code
        binding.progressView.root.isVisible = true
        viewModel.obtainCredential(safeArgs.qrLink)
    }
}