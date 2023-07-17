package by.alexandr7035.affinidi_id.presentation.profile.edit_password

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.databinding.FragmentChangePasswordBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    private val binding by viewBinding(FragmentChangePasswordBinding::bind)
    private val viewModel by viewModels<ChangePasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.oldPasswordEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.oldPasswordField.clearError()
            }
        }

        binding.newPasswordEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.newPasswordField.clearError()
            }
        }


        binding.confirmBtn.setOnClickListener {
            if (checkIfFormIsValid()) {
                binding.progressView.root.isVisible = true

                viewModel.changePassword(
                    oldPassword = binding.oldPasswordEditText.text.toString(),
                    newPassword = binding.newPasswordEditText.text.toString()
                )
            }
        }

        viewModel.changePasswordLiveData.observe(viewLifecycleOwner) { result ->
            binding.progressView.root.isVisible = false

            when (result) {
                is ChangePasswordResModel.Success -> {
                    binding.root.showSnackBar(
                        message = getString(R.string.password_changed_successfully),
                        snackBarMode = SnackBarMode.Neutral,
                        snackBarLength = Snackbar.LENGTH_SHORT
                    )

                    findNavController().popBackStack()
                }
                is ChangePasswordResModel.Fail -> {
                    when (result.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }

                        ErrorType.WRONG_CURRENT_PASSWORD -> {
                            binding.oldPasswordField.error = getString(R.string.error_wrong_current_password)
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
        }
    }

    private fun checkIfFormIsValid(): Boolean {
        var formIsValid = true

        val oldPassword = binding.oldPasswordEditText.text.toString()
        val newPassword = binding.newPasswordEditText.text.toString()

        when (viewModel.validatePassword(oldPassword)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.oldPasswordField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.oldPasswordField.error = getString(R.string.error_wrong_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {}
        }

        when (viewModel.validatePassword(newPassword)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.newPasswordField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.newPasswordField.error = getString(R.string.error_wrong_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {}
        }

        return formIsValid
    }

}