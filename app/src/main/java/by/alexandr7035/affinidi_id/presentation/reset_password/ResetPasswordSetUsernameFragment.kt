package by.alexandr7035.affinidi_id.presentation.reset_password

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.databinding.FragmentResetPasswordSetUsernameBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import hideKeyboard
import java.util.Locale

@AndroidEntryPoint
class ResetPasswordSetUsernameFragment : Fragment(R.layout.fragment_reset_password_set_username) {

    private val binding by viewBinding(FragmentResetPasswordSetUsernameBinding::bind)
    private val viewModel by navGraphViewModels<ResetPasswordViewModel>(R.id.resetPasswordGraph) { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.userNameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.userNameField.clearError()
            }
        }

        binding.continueBtn.setOnClickListener {
            if (checkIfFormIsValid()) {
                hideKeyboard()

                binding.progressView.root.isVisible = true

                val userName = binding.userNameEditText.text.toString().lowercase(Locale.getDefault())
                viewModel.initializePasswordReset(userName)
            }
        }

        viewModel.initializePasswordResetLiveData.observe(viewLifecycleOwner) { result ->
            binding.progressView.root.isVisible = false

            when (result) {
                is InitializePasswordResetResponseModel.Success -> {
                    findNavController().navigateSafe(
                        ResetPasswordSetUsernameFragmentDirections
                            .actionResetPasswordSetUsernameFragmentToResetPasswordSetPasswordFragment(result.userName)
                    )
                }

                is InitializePasswordResetResponseModel.Fail -> {
                    when (result.errorType) {
                        ErrorType.USER_DOES_NOT_EXIST -> {
                            binding.userNameField.error = getString(R.string.error_user_not_found)
                        }
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }
                        // Including unknown error
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

        val userName = binding.userNameEditText.text.toString()
        when (viewModel.validateUserName(userName)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.userNameField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.userNameField.error = getString(R.string.error_invalid_user_name)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {
            }
        }

        return formIsValid
    }

}