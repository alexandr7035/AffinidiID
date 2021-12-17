package by.alexandr7035.affinidi_id.presentation.login

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.getClickableSpannable
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.data.model.SignInModel
import by.alexandr7035.affinidi_id.databinding.FragmentLoginBinding
import by.alexandr7035.affinidi_id.presentation.helpers.InputValidationResult
import by.alexandr7035.affinidi_id.presentation.helpers.InputValidatorImpl
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

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
                binding.loginProgressView.isVisible = true

                viewModel.signIn(
                    userName = binding.userNameEditText.text.toString(),
                    password = binding.passwordEditText.text.toString()
                )
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

            binding.loginProgressView.isVisible = false

            when (response) {

                // Auth successful
                is SignInModel.Success -> {
                    // API doesn't provide username
                    // So save it manually
                    viewModel.saveUserName(binding.userNameEditText.text.toString())
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
                            Toast.makeText(requireContext(), getString(R.string.error_failed_connection), Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), getString(R.string.error_unknown), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })

//        binding.goToSignUpBtn.text =

        val goToSignUpText = getString(R.string.go_to_sign_up)
        val spannable = goToSignUpText.getClickableSpannable(
            clickableText = getString(R.string.go_to_sign_up_clickable),
            clickListener = {
                findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            },
            isBold = true,
            spannableColor = ContextCompat.getColor(requireContext(), R.color.white)
        )

        binding.goToSignUpBtn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    private fun chekIfFormIsValid(): Boolean {

        // FIXME move from here to viewmodel (?)
        val validator = InputValidatorImpl()

        var isValid = true

        // FIXME use validator
        if (binding.userNameEditText.text!!.isEmpty()) {
            binding.userNameField.error = getString(R.string.error_empty_field)
            isValid = false
        }

        val password = binding.passwordEditText.text.toString()

        when (validator.validatePassword(password)) {
            InputValidationResult.PASSWORD_IS_EMPTY -> {
                binding.passwordField.error = getString(R.string.error_empty_field)
                isValid = false
            }

            InputValidationResult.PASSWORD_WRONG_FORMAT -> {
                binding.passwordField.error = getString(R.string.error_wromg_password_format)
                isValid = false
            }
        }

        return isValid
    }

}