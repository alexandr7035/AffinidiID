package by.alexandr7035.affinidi_id.presentation.issue_credential

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.databinding.FragmentIssueCredentialBinding
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types.AvailableVcType
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IssueCredentialFragment : Fragment(), CredentialClickListener {

    private val viewModel by viewModels<IssueCredentialViewModel>()
    private val binding by viewBinding(FragmentIssueCredentialBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = AvailableCredentialsAdapter(this)
        binding.recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = layoutManager

        // Decoration (spacing) for menu items
        val decoration = DividerItemDecoration(
            binding.recycler.context,
            layoutManager.orientation
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.credential_item_bottom_decoration)?.let {
            decoration.setDrawable(it)
        }
        binding.recycler.addItemDecoration(decoration)

        viewModel.getAvailableVCsLiveData().observe(viewLifecycleOwner, {
            adapter.setItems(it)
        })

        viewModel.loadAvailableVCs()


        viewModel.getIssueCredentialLiveData().observe(viewLifecycleOwner, { result ->
            binding.progressView.root.isVisible = false

            // TODO normal message
            when (result) {
                is IssueCredentialResModel.Success -> {
                    requireContext().showToast("Issue is successfull")
                }
                is IssueCredentialResModel.Fail -> {
                    requireContext().showToast(result.errorType.name)
                }
            }
        })
    }


    // Handle credential issuance request here
    // TODO confirmation
    override fun onClick(availableVcType: AvailableVcType) {
        binding.progressView.root.isVisible = true
        viewModel.issueCredential(availableVcType)
    }
}