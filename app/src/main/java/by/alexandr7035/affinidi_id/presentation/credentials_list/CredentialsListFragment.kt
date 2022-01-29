package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialsListBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CredentialsListFragment : Fragment(), CredentialClickListener {

    private val binding by viewBinding(FragmentCredentialsListBinding::bind)
    private val viewModel by viewModels<CredentialsListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credentials_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.debug("onViewCreated creds list")

        val adapter = CredentialsAdapter(credentialClickListener = this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter

        // Decoration (spacing) for menu items
        val decoration = DividerItemDecoration(
            binding.recycler.context,
            layoutManager.orientation
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.credential_item_bottom_decoration)?.let {
            decoration.setDrawable(it)
        }
        binding.recycler.addItemDecoration(decoration)

        viewModel.getCredentialsLiveData().observe(viewLifecycleOwner, {
            // Hide all before state update
            binding.progressView.root.isVisible = false
            binding.recycler.isVisible = false
            binding.noCredentialsStub.root.isVisible = false
            binding.errorView.root.isVisible = false

            when (it) {
                is CredentialListUiModel.Loading -> {
                    binding.progressView.root.isVisible = true
                }

                is CredentialListUiModel.Success -> {
                    binding.recycler.isVisible = true
                    adapter.setItems(it.credentials)
                }

                is CredentialListUiModel.NoCredentials -> {
                    binding.noCredentialsStub.root.isVisible = true
                }

                is CredentialListUiModel.Fail -> {
                    binding.errorView.root.isVisible = true

                    binding.errorView.errorTitle.text = it.errorUi.title
                    binding.errorView.errorText.text = it.errorUi.message
                }
            }
        })

        loadData()

        binding.errorView.retryBtn.setOnClickListener {
            loadData()
        }

        binding.addCredentialBtn.setOnClickListener {
            findNavController().navigateSafe(CredentialsListFragmentDirections
                .actionCredentialsListFragmentToIssueCredentialFragment())
        }
    }

    private fun loadData() {
        binding.errorView.root.isVisible = false
        viewModel.load()
    }

    override fun onCredentialClicked(credentialId: String) {
        findNavController().navigateSafe(CredentialsListFragmentDirections
            .actionCredentialsListFragmentToCredentialDetailsFragment(credentialId))
    }
}