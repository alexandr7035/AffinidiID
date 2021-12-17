package by.alexandr7035.affinidi_id.presentation.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.model.LogOutModel
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
            binding.userDidView.text = profile.userDid
            Timber.debug("DID ${profile.userDid}")

            binding.profileImageView.load(
                uri = viewModel.getProfileImageUrl(profile.userDid),
                imageLoader = imageLoader
            )
        })


        viewModel.logOutLiveData.observe(viewLifecycleOwner, { logout ->
            when (logout) {
                is LogOutModel.Success -> {
                    requireContext().showToast(getString(R.string.successful_logout))
                    findNavController().navigateSafe(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                }
                is LogOutModel.Fail -> {
                    when (logout.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            requireContext().showToast(getString(R.string.error_failed_connection))
                        }
                        ErrorType.UNKNOWN_ERROR -> {
                            requireContext().showToast(getString(R.string.error_unknown))
                        }
                        else -> {
                            requireContext().showToast(getString(R.string.error_unknown))
                        }
                    }
                }
            }
        })

        viewModel.init()

        binding.userDidView.setOnClickListener {
            val clipLabel = getString(R.string.your_did_copied)

            val clipBoard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
            clipBoard?.setPrimaryClip(
                ClipData.newPlainText(
                    clipLabel,
                    binding.userDidView.text.toString()
                )
            )

            Toast.makeText(requireContext(), clipLabel, Toast.LENGTH_LONG).show()
        }

        binding.toolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.logoutItem -> {
                    // TODO dialog
                    viewModel.logOut()
                }
            }

            true
        }
    }
}