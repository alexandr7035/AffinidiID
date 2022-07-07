package by.alexandr7035.affinidi_id.presentation.credential_details.claims

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialClaimsBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDetailsViewModel
import by.kirich1409.viewbindingdelegate.viewBinding


class CredentialClaimsFragment : Fragment() {

//    private val viewModel by lazy {
//        ViewModelProvider(requireActivity()).get(CredentialDetailsViewModel::class.java)
//    }

    private val binding by viewBinding(FragmentCredentialClaimsBinding::bind)
    private val sharedViewModel by viewModels<CredentialDetailsViewModel>(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_claims, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getCredentialLiveData().observe(viewLifecycleOwner) { credentialDetails ->

            when (credentialDetails) {
                is CredentialDetailsUi.Success -> {
                    binding.credentialCard.credentialId.text = credentialDetails.credentialCardUi.id
                    binding.credentialCard.credentialTypeView.text = credentialDetails.credentialCardUi.credentialTypeText
                    binding.credentialCard.credentialExpires.text = credentialDetails.credentialCardUi.credentialStatusText

                    // Set other card color when VC is expired
                    if (credentialDetails.credentialCardUi.credentialStatus != CredentialStatus.ACTIVE) {
                        binding.credentialCard.root.background = ContextCompat
                            .getDrawable(requireContext(), R.drawable.background_credential_item_secondary)
                    }
                }

                is CredentialDetailsUi.Loading -> {
                    // Handled in parent fragment
                }

                is CredentialDetailsUi.Fail -> {
                    // Handled in parent fragment
                }
            }
        }
    }


}