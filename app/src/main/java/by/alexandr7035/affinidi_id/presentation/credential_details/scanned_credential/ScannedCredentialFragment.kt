package by.alexandr7035.affinidi_id.presentation.credential_details.scanned_credential

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentScannedCredentialBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.VibrationMode
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationModelUi
import by.alexandr7035.affinidi_id.presentation.credential_details.LoadCredentialDetailsViewModel
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialViewPagerAdapter
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.alexandr7035.affinidi_id.presentation.verify_credential.VerificationViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannedCredentialFragment : Fragment() {

    private val detailsViewModel by viewModels<LoadCredentialDetailsViewModel>()
    private val verifyViewModel by viewModels<VerificationViewModel>()
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


        detailsViewModel.getCredentialLiveData().observe(viewLifecycleOwner) { credentialData ->
            binding.progressView.root.isVisible = false

            when (credentialData) {
                is CredentialDetailsUi.Success -> {

                    val pagerAdapter = CredentialViewPagerAdapter(
                        parentFragment = this,
                        tabsCount = 2
                    )

                    binding.viewPager.adapter = pagerAdapter

                    val tabTitles = listOf(
                        getString(R.string.claims),
                        getString(R.string.proof)
                    )

                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabTitles[position]
                    }.attach()

                    binding.verifyBtn.setOnClickListener {
                        binding.progressView.root.isVisible = true
                        verifyViewModel.verifyCredential(credentialData.rawVcDataPrettyFormatted)
                    }

                    binding.holderLabel.text = getString(
                        R.string.holder_did_template,
                        credentialData.credentialCardUi.holderDid
                    )
                }

                is CredentialDetailsUi.Loading -> {
                    binding.progressView.root.isVisible = true
                }

                is CredentialDetailsUi.Fail -> {
                    // Show unknown error always
                    // Connection error is unlikely to be thrown as credential is already cached
                    showErrorDialog(
                        getString(R.string.error_unknown_title),
                        getString(R.string.error_unknown)
                    )
                }
            }
        }


        verifyViewModel.getVerificationLiveData().observe(viewLifecycleOwner) { verificationResult ->
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
        detailsViewModel.obtainCredential(safeArgs.qrLink)
    }

}