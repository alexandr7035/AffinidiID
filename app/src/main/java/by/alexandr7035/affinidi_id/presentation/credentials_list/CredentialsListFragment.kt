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
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.credentials_list.filters.CredentialFilters
import by.alexandr7035.affinidi_id.presentation.credentials_list.model.CredentialListUiModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CredentialsListFragment : Fragment() {

    private val binding by viewBinding(FragmentCredentialsListBinding::bind)
    private val viewModel by viewModels<CredentialsListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credentials_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.debug("onViewCreated creds list")

        val adapter = CredentialsAdapter(credentialClickCallback = { credentialId ->
            findNavController().navigateSafe(
                CredentialsListFragmentDirections.actionCredentialsListFragmentToCredentialDetailsFragment(credentialId)
            )
        })


        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter

        // Decoration (spacing) for menu items
        val decoration = DividerItemDecoration(
            binding.recycler.context, layoutManager.orientation
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.credential_item_bottom_decoration)?.let {
            decoration.setDrawable(it)
        }
        binding.recycler.addItemDecoration(decoration)

        viewModel.getCredentialsLiveData().observe(viewLifecycleOwner) { vcsRes ->
            // Hide all before state update
            binding.progressView.root.isVisible = false
            binding.recycler.isVisible = false
            binding.noCredentialsStub.root.isVisible = false
            binding.errorView.root.isVisible = false

            when (vcsRes) {
                is CredentialListUiModel.Loading -> {
                    binding.progressView.root.isVisible = true
                }

                is CredentialListUiModel.Success -> {
                    binding.recycler.isVisible = true

                    val filter = getFiltersForTab(binding.tabLayout.selectedTabPosition)
                    val filteredVc = if (filter != null) {
                        vcsRes.credentials.filter {
                            it.credentialStatusUi.domainStatus === filter
                        }
                    } else {
                        vcsRes.credentials
                    }

                    if (filteredVc.isEmpty()) {
                        val type = if (filter == CredentialStatus.Active) "Active" else "Expired"

                        binding.noCredentialsStub.root.text = getString(R.string.no_credentials_filtered_stub_text, type)
                        binding.noCredentialsStub.root.isVisible = true
                    }

                    adapter.setItems(filteredVc)
                }

                is CredentialListUiModel.NoCredentials -> {
                    binding.noCredentialsStub.root.text = getString(R.string.no_credentials_stub_text)
                    binding.noCredentialsStub.root.isVisible = true
                }

                is CredentialListUiModel.Fail -> {
                    binding.errorView.root.isVisible = true

                    binding.errorView.errorTitle.text = vcsRes.errorUi.title
                    binding.errorView.errorText.text = vcsRes.errorUi.message
                }
            }
        }

        // Initial load
        loadData()

        // Retry load
        binding.errorView.retryBtn.setOnClickListener {
            loadData()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                loadData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                loadData()
            }
        })


        binding.addCredentialBtn.setOnClickListener {
            findNavController().navigateSafe(
                CredentialsListFragmentDirections.actionCredentialsListFragmentToIssueCredentialFragment()
            )
        }
    }

    private fun loadData() {
        binding.errorView.root.isVisible = false
        viewModel.load()
    }

    private fun getFiltersForTab(tabPosition: Int): CredentialStatus? {
        return when (tabPosition) {
            0 -> null
            1 -> CredentialStatus.Active
            2 -> CredentialStatus.Expired
            else -> throw RuntimeException("No such tab implemented")
        }
    }
}