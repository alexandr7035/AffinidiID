package by.alexandr7035.affinidi_id.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.databinding.FragmentProfileBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageLoader = ImageLoader.Builder(requireContext())
            .componentRegistry {
                add(SvgDecoder(requireContext()))
            }
            .build()


        viewModel.userProfileLiveData.observe(viewLifecycleOwner, { profile ->
            binding.userNameView.text = profile.userName
            Timber.debug("DID ${profile.userDid}")

            binding.profileImageView.load(
                uri = viewModel.getProfileImageUrl(profile.userDid),
                imageLoader = imageLoader
            )
        })

        viewModel.init()

    }
}