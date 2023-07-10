package by.alexandr7035.affinidi_id.presentation.profile

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.copyToClipboard
import by.alexandr7035.affinidi_id.core.extensions.getNavigationResult
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.core.extensions.svgLoader
import by.alexandr7035.affinidi_id.databinding.FragmentProfileBinding
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()
    private val binding by viewBinding(FragmentProfileBinding::bind)

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

        binding.scanCredentialBtn.setOnClickListener {
            processScanBtn()
        }

        getNavigationResult<String>(R.id.profileFragment, "qrCode") { qrCode ->
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToScannedCredentialFragment(
                    qrCode
                )
            )
        }
    }

    private fun processScanBtn() {
        PermissionX.init(requireActivity())
            .permissions(Manifest.permission.CAMERA)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    getString(R.string.permission_camera_explanation),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.permission_camera_explanation_denied),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    findNavController().navigateSafe(
                        ProfileFragmentDirections.actionProfileFragmentToScanCredentialFragment()
                    )
                } else {
                    binding.root.showSnackBar(
                        getString(R.string.permission_denied),
                        snackBarLength = Snackbar.LENGTH_SHORT,
                        snackBarMode = SnackBarMode.Negative
                    )
                }
            }
    }
}