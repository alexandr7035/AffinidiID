package by.alexandr7035.affinidi_id.presentation.credential_details.claims

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialClaimsBinding
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDetailsUiModel
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

        sharedViewModel.getCredentialLiveData().observe(viewLifecycleOwner) { credentialData ->

            when (credentialData) {
                is CredentialDetailsUiModel.Success -> {
//                    binding.credentialCard.credentialId.text = credentialData.credentialId
//                    binding.credentialCard.credentialExpires = credentialDat
                }

                is CredentialDetailsUiModel.Loading -> {

                }

                is CredentialDetailsUiModel.Fail -> {

                }
            }
        }
    }


}