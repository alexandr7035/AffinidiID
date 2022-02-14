package by.alexandr7035.affinidi_id.presentation.registration

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.getClickableSpannable
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel by navGraphViewModels<RegistrationViewModel>(R.id.signUpGraph) { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.signUpBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.progressView.root.isVisible = true

                val username = binding.userNameEditText.text.toString().lowercase(Locale.getDefault())
                val password = binding.passwordSetEditText.text.toString()
                viewModel.signUp(userName = username, password = password)
            }
        }


        viewModel.signUpLiveData.observe(viewLifecycleOwner, { signUpResult ->

            when (signUpResult) {
                is SignUpResponseModel.Success -> {
                    findNavController()
                        .navigateSafe(
                            RegistrationFragmentDirections.actionRegistrationFragmentToRegistrationConfirmationFragment(
                                signUpResult.confirmSignUpToken
                            )
                        )
                }
                is SignUpResponseModel.Fail -> {
                    binding.progressView.root.isVisible = false

                    when (signUpResult.errorType) {
                        ErrorType.USER_ALREADY_EXISTS -> {
                            binding.userNameField.error = getString(R.string.error_user_exists)
                        }
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }
                        // Including UNKNOWN_ERROR
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


        binding.userNameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.userNameField.clearError()
            }
        }

        binding.passwordSetEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.passwordSetField.clearError()
            }
        }

        binding.passwordConfirmEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.passwordConfirmField.clearError()
            }
        }

        val goToSignUpText = getString(R.string.go_to_sign_in)
        val spannable = goToSignUpText.getClickableSpannable(
            clickableText = getString(R.string.go_to_sign_in_clickable),
            clickListener = {
                findNavController().navigateUp()
            },
            isBold = true,
            spannableColor = ContextCompat.getColor(requireContext(), R.color.gray_500)
        )

        binding.goToSignInBtn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }


        viewModel.signUpConfirmationLiveData.observe(viewLifecycleOwner, { signUpConfirmationResult ->
            when (signUpConfirmationResult) {

                is ConfirmSignUpResponseModel.Success -> {
                    // FIXME handle in models
                    viewModel.saveProfile(binding.userNameEditText.text.toString(), signUpConfirmationResult.userDid)
                    findNavController().navigateSafe(RegistrationConfirmationFragmentDirections.actionGlobalProfileFragment())
                }

                is ConfirmSignUpResponseModel.Fail -> {
                    binding.progressView.root.isVisible = false

                    when (signUpConfirmationResult.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }
                        ErrorType.WRONG_CONFIRMATION_CODE -> {
                            showErrorDialog(
                                getString(R.string.error_wrong_confirmation_code_title),
                                getString(R.string.error_wrong_confirmation_code)
                            )
                        }
                        ErrorType.CONFIRMATION_CODE_DIALOG_DISMISSED -> {
                            // DO NOTHING
                            // Type used just to be caught here and hide progressbar
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

        val password = binding.passwordSetEditText.text.toString()
        val passwordConfirmation = binding.passwordConfirmEditText.text.toString()

        if (password != passwordConfirmation) {
            binding.passwordConfirmField.error = getString(R.string.error_passwords_not_match)
            binding.passwordSetField.error = getString(R.string.error_passwords_not_match)
            formIsValid = false
        }


        when (viewModel.validatePassword(password)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.passwordSetField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.passwordSetField.error = getString(R.string.error_wrong_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {
            }
        }

        when (viewModel.validatePassword(passwordConfirmation)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.passwordConfirmField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.passwordConfirmField.error = getString(R.string.error_wrong_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {
            }
        }

        return formIsValid
    }

}