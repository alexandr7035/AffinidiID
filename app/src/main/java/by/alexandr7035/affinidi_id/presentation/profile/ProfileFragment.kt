package by.alexandr7035.affinidi_id.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.copyToClipboard
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.core.extensions.svgLoader
import by.alexandr7035.affinidi_id.databinding.FragmentProfileBinding
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.userProfileLiveData.observe(viewLifecycleOwner) { profile ->
            binding.userNameView.text = profile.userName
            // TODO profile ui model
            val formattedDid = profile.userDid.split(";").first()
            binding.userDidView.text = formattedDid

            binding.profileImageView.load(
                data = profile.imageUrl,
                imageLoader = requireContext().svgLoader()
            )
        }

        viewModel.init()

        binding.copyUserNameBtn.setOnClickListener {
            val clipLabel = getString(R.string.your_username_copied)
            binding.userNameView.copyToClipboard(clipLabel)

            binding.root.showSnackBar(clipLabel, SnackBarMode.Neutral, Snackbar.LENGTH_SHORT)
        }

        binding.copyUserDidBtn.setOnClickListener {
            val clipLabel = getString(R.string.your_did_copied)
            binding.userDidView.copyToClipboard(clipLabel)

            binding.root.showSnackBar(clipLabel, SnackBarMode.Neutral, Snackbar.LENGTH_SHORT)
        }

    }
}