package by.alexandr7035.affinidi_id.presentation.reset_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.*
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmPasswordResetModel
import by.alexandr7035.affinidi_id.databinding.FragmentResetPasswordConfirmationBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Error

@AndroidEntryPoint
class ResetPasswordConfirmationFragment : Fragment() {

    private val binding by viewBinding(FragmentResetPasswordConfirmationBinding::bind)
    private val viewModel by viewModels<ResetPasswordViewModel>()
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
                binding.progressView.isVisible = true
                Timber.debug("password ${safeArgs.newPassword} username ${safeArgs.userName}")
                viewModel.confirmPasswordReset(
                    username = safeArgs.userName,
                    newPassword = safeArgs.newPassword,
                    confirmationCode = binding.confirmationCodeEditText.text.toString()
                )
            }
        }


        viewModel.confirmPasswordResetLiveData.observe(viewLifecycleOwner, { result ->
            binding.progressView.isVisible = false

            when (result) {
                is ConfirmPasswordResetModel.Success -> {
                    findNavController().navigateSafe(ResetPasswordConfirmationFragmentDirections
                        // TODO show success dialog
                        .actionResetPasswordConfirmationFragmentToLoginFragment())
                }

                is ConfirmPasswordResetModel.Fail -> {
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