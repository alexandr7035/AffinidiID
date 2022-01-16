package by.alexandr7035.affinidi_id.presentation.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentResetPasswordConfirmationBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordConfirmationFragment : Fragment() {

    private val binding by viewBinding(FragmentResetPasswordConfirmationBinding::bind)
    private val viewModel by navGraphViewModels<ResetPasswordViewModel>(R.id.resetPasswordGraph) { defaultViewModelProviderFactory }
    private val safeArgs by navArgs<ResetPasswordConfirmationFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmationCodeEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.confirmationCodeField.clearError()
            }
        }

        binding.confirmBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.progressView.root.isVisible = true

                viewModel.confirmPasswordReset(
                    username = safeArgs.userName,
                    newPassword = safeArgs.newPassword,
                    confirmationCode = binding.confirmationCodeEditText.text.toString()
                )
            }
        }


        viewModel.confirmPasswordResetLiveData.observe(viewLifecycleOwner, { result ->
            binding.progressView.root.isVisible = false

            when (result) {
                is ConfirmPasswordResetResponseModel.Success -> {
                    findNavController().navigateSafe(ResetPasswordConfirmationFragmentDirections
                        .actionGlobalLoginFragment())

                    // TODO show success dialog
                    requireContext().showToast(getString(R.string.password_changed_successfully))
                }

                is ConfirmPasswordResetResponseModel.Fail -> {
                    when (result.errorType) {
                        ErrorType.WRONG_CONFIRMATION_CODE -> {
                            binding.confirmationCodeField.error = getString(R.string.error_wrong_confirmation_code)
                        }

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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun chekIfFormIsValid(): Boolean {
        var formIsValid = true

        val code = binding.confirmationCodeEditText.text.toString()
        when (viewModel.validateConfirmationCode(code)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.confirmationCodeField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {
            }
        }

        return formIsValid
    }
}