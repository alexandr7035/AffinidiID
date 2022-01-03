package by.alexandr7035.affinidi_id.presentation.main_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.alexandr7035.affinidi_id.BuildConfig
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.databinding.FragmentMainMenuBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import timber.log.Timber

class MainMenuFragment : Fragment() {

    private val binding by viewBinding(FragmentMainMenuBinding::bind)
    private val safeArgs by navArgs<MainMenuFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
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
                    findNavController().navigateSafe(MainMenuFragmentDirections
                        .actionMainMenuFragmentToEditUserNameFragment())
                }),

            MenuItemModel(
                title = getString(R.string.change_password),
                clickListener = {
                    findNavController().navigateSafe(MainMenuFragmentDirections
                        .actionMainMenuFragmentToChangePasswordFragment())
                }),
            MenuItemModel(
                title = getString(R.string.reset_password),
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

        val adapter = MainProfileMenuAdapter(menuItems)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager


        binding.appVersionView.text = getString(
            R.string.app_name_with_version,
            BuildConfig.VERSION_NAME
        )

        val imageLoader = ImageLoader.Builder(requireContext())
            .componentRegistry {
                add(SvgDecoder(requireContext()))
            }
            .build()

        binding.profileImageView.load(
            uri = safeArgs.profileImageUrl,
            imageLoader = imageLoader
        )
    }
}