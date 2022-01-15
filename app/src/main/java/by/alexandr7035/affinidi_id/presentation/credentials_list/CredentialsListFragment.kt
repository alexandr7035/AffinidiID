package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialsListBinding
import by.kirich1409.viewbindingdelegate.viewBinding
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

        val adapter = CredentialsAdapter()
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
            adapter.setItems(it)
        })

        viewModel.load()
    }
}