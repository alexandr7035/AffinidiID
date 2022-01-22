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
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialDetailsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
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

        viewModel.getCredentialLiveData().observe(viewLifecycleOwner, { credentialFields ->
            binding.progressView.root.isVisible = false

            adapter.setItems(credentialFields)
        })

        // Load data
        load(safeArgs.credentialId)
    }

    private fun load(credentialId: String) {
        binding.progressView.progressView.isVisible = true
        viewModel.load(credentialId)
    }
}