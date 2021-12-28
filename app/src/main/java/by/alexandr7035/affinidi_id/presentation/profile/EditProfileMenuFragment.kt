package by.alexandr7035.affinidi_id.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.databinding.FragmentEditProfileMenuBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber

class EditProfileMenuFragment : Fragment() {

    private val binding by viewBinding(FragmentEditProfileMenuBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val menuItems = listOf(
            MenuItemModel(
                title = getString(R.string.change_username),
                clickListener = {
                    Timber.debug("test menu")
                }),

            MenuItemModel(
                title = getString(R.string.change_password),
                clickListener = {
                    Timber.debug("test menu")
                })
        )


        val layoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(
            binding.recycler.context,
            layoutManager.orientation
        )

        ContextCompat.getDrawable(requireContext(), R.drawable.primary_menu_item_decoration)?.let {
            decoration.setDrawable(it)
        }

        binding.recycler.addItemDecoration(decoration)

        val adapter = EditProfileMenuAdapter(menuItems)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager
    }
}