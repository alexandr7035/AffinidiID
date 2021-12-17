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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.getClickableSpannable
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationBinding
import by.alexandr7035.affinidi_id.presentation.helpers.InputValidationResult
import by.alexandr7035.affinidi_id.presentation.helpers.InputValidatorImpl
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val formValidator = InputValidatorImpl()

        binding.signUpBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.loginProgressView.isVisible = true

                viewModel.signUp(
                    binding.userNameEditText.text.toString(),
                    binding.passwordSetEditText.text.toString())
            }
        }


        viewModel.signUpLiveData.observe(viewLifecycleOwner, { signUpResult ->
            binding.loginProgressView.isVisible = false

            when (signUpResult) {
                is SignUpModel.Success -> {
                    requireContext().showToast(signUpResult.confirmSignUpToken)
                    findNavController()
                        .navigateSafe(RegistrationFragmentDirections.actionRegistrationFragmentToRegistrationConfirmationFragment(
                            signUpResult.confirmSignUpToken
                        ))
                }
                is SignUpModel.Fail -> {
                    handleAPIError(signUpResult.errorType)
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
            spannableColor = ContextCompat.getColor(requireContext(), R.color.white)
        )

        binding.goToSignInBtn.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    private fun handleAPIError(errorType: ErrorType) {
        requireContext().showToast(errorType.name)

        // TODO handle other errors
        when (errorType) {
            ErrorType.USER_ALREADY_EXISTS -> {
                binding.userNameField.error = getString(R.string.error_user_exists)
            }
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

        val password = binding.passwordSetEditText.text.toString()
        val passwordConfirmation = binding.passwordConfirmEditText.text.toString()

        if (password != passwordConfirmation) {
            binding.passwordConfirmField.error = getString(R.string.error_passwords_not_match)
            binding.passwordSetField.error = getString(R.string.error_passwords_not_match)
            isValid = false
        }


        when (validator.validatePassword(password)) {
            InputValidationResult.PASSWORD_IS_EMPTY -> {
                binding.passwordSetField.error = getString(R.string.error_empty_field)
                isValid = false
            }

            InputValidationResult.PASSWORD_WRONG_FORMAT -> {
                binding.passwordSetField.error = getString(R.string.error_wromg_password_format)
                isValid = false
            }
        }

        when (validator.validatePassword(passwordConfirmation)) {
            InputValidationResult.PASSWORD_IS_EMPTY -> {
                binding.passwordConfirmField.error = getString(R.string.error_empty_field)
                isValid = false
            }

            InputValidationResult.PASSWORD_WRONG_FORMAT -> {
                binding.passwordConfirmField.error = getString(R.string.error_wromg_password_format)
                isValid = false
            }
        }

        return isValid
    }
}