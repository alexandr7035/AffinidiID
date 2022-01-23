package by.alexandr7035.affinidi_id.presentation.delete_credential

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.databinding.FragmentDeleteCredentialBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCredentialFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentDeleteCredentialBinding::bind)
    private val viewModel by viewModels<DeleteCredentialViewModel>()
    private val safeArgs by navArgs<DeleteCredentialFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDeleteVcLiveData().observe(viewLifecycleOwner, { result ->
            binding.progressView.progressView.isVisible = false

            when (result) {
                is DeleteVcResModel.Success -> {
                    findNavController().navigateSafe(DeleteCredentialFragmentDirections
                        .actionDeleteCredentialFragmentToCredentialsListFragment())
                }
                is DeleteVcResModel.Fail -> {
                    when (result.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }

                        else -> {
                            showErrorDialog(
                                getString(R.string.error_unknown_title),
                                getString(R.string.error_unknown)
                            )
                        }
                    }

                }
            }
        })

        binding.confirmDeleteBtn.setOnClickListener {
            binding.progressView.progressView.isVisible = true
            viewModel.deleteVc(safeArgs.deleteVcId)
        }
    }
}