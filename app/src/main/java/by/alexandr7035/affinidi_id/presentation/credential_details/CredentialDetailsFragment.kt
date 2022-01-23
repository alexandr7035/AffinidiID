package by.alexandr7035.affinidi_id.presentation.credential_details

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
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.core.extensions.vibrate
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialDetailsBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CredentialDetailsFragment : Fragment() {

    private val binding by viewBinding(FragmentCredentialDetailsBinding::bind)
    private val viewModel by viewModels<CredentialDetailsViewModel>()
    private val safeArgs by navArgs<CredentialDetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = CredentialDataAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCredentialLiveData().observe(viewLifecycleOwner, { credentialData ->
            binding.progressView.root.isVisible = false

            when (credentialData) {
                is CredentialDetailsUiModel.Success -> {
                    // Set fields list
                    adapter.setItems(credentialData.detailsItems)

                    binding.verifyBtn.setOnClickListener {
                        binding.progressView.root.isVisible = true
                        viewModel.verifyCredential(credentialData.rawVcData)
                    }
                }

                is CredentialDetailsUiModel.Loading -> {
                    binding.progressView.root.isVisible = true
                }

                is CredentialDetailsUiModel.Fail -> {
                    // Show unknown error always
                    // Connection error is unlikely to be thrown as credential is already cached
                    showErrorDialog(
                        getString(R.string.error_unknown_title),
                        getString(R.string.error_unknown)
                    )
                }
            }

        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_item -> {
                    findNavController().navigateSafe(
                        CredentialDetailsFragmentDirections.actionCredentialDetailsFragmentToDeleteCredentialFragment(
                            safeArgs.credentialId
                        )
                    )
                }
            }

            true
        }

        // Load credential data
        load(safeArgs.credentialId)

        viewModel.getVerificationLiveData().observe(viewLifecycleOwner, { verificationResult ->
            binding.progressView.root.isVisible = false

            when (verificationResult) {
                is VerificationModelUi.Success -> {
                    binding.root.showSnackBar(
                        message = verificationResult.messageText,
                        snackBarLength = Snackbar.LENGTH_LONG,
                        isPositive = verificationResult.isValid
                    )

                    // TODO int resource
                    requireContext().vibrate(vibrationTimeMills = 200)
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
        })
    }

    private fun load(credentialId: String) {
        binding.progressView.progressView.isVisible = true
        viewModel.loadCredential(credentialId)
    }
}