package by.alexandr7035.affinidi_id.presentation.credential_details.more_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialProofBinding
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDetailsViewModel
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialFieldsAdapter
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.kirich1409.viewbindingdelegate.viewBinding


class CredentialProofFragment : Fragment() {

    private val sharedViewModel by viewModels<CredentialDetailsViewModel>(
        ownerProducer = { requireParentFragment() }
    )
    private val binding by viewBinding(FragmentCredentialProofBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_proof, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val proofAdapter = CredentialFieldsAdapter()
        binding.proofRecycler.adapter = proofAdapter
        binding.proofRecycler.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel.getCredentialLiveData().observe(viewLifecycleOwner) { credentialDetails ->
            when (credentialDetails) {
                is CredentialDetailsUi.Success -> {
                    proofAdapter.setItems(credentialDetails.proofItems)
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