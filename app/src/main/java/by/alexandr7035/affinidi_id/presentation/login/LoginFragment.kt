package by.alexandr7035.affinidi_id.presentation.login

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.getClickableSpannable
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentLoginBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.SignInModel
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {

            // Clear errors before request
            binding.userNameField.clearError()
            binding.passwordField.clearError()

            if (chekIfFormIsValid()) {
                binding.progressView.root.isVisible = true

                val username = binding.userNameEditText.text.toString().lowercase(Locale.getDefault())
                val password = binding.passwordEditText.text.toString()
                viewModel.signIn(userName = username, password = password)
            }
        }

        binding.userNameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.userNameField.clearError()
            }
        }

        binding.passwordEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.passwordField.clearError()
            }
        }


        viewModel.signInLiveData.observe(viewLifecycleOwner, { response ->

            binding.progressView.root.isVisible = false

            when (response) {

                // Auth successful
                is SignInModel.Success -> {
                    viewModel.saveProfile(userName = response.userName, userDid = response.userDid)
                    findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                }

                // Auth failed
                is SignInModel.Fail -> {
                    when (response.errorType) {
                        ErrorType.USER_DOES_NOT_EXIST -> {
                            binding.userNameField.error = getString(R.string.error_user_not_found)
                        }
                        ErrorType.WRONG_USERNAME_OR_PASSWORD -> {
                            binding.userNameField.error = getString(R.string.error_wrong_user_or_password)
                            binding.passwordField.error = getString(R.string.error_wrong_user_or_password)
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
        })


        val goToSignUpText = getString(R.string.go_to_sign_up)
        val spannable = goToSignUpText.getClickableSpannable(
            clickableText = getString(R.string.go_to_sign_up_clickable),
            clickListener = {
                findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToSignUpGraph())
            },
            isBold = true,
            spannableColor = ContextCompat.getColor(requireContext(), R.color.gray_500)
        )

        binding.goToSignUpBtn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        binding.forgotPasswordBtn.setOnClickListener {
            findNavController()
                .navigateSafe(LoginFragmentDirections.actionLoginFragmentToResetPasswordGraph())
        }
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

            InputValidationResult.NO_ERRORS -> {}
        }

        val password = binding.passwordEditText.text.toString()
        when (viewModel.validatePassword(password)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.passwordField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.passwordField.error = getString(R.string.error_wrong_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {}
        }

        return formIsValid
    }
}