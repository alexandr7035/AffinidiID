package by.alexandr7035.affinidi_id.presentation.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentResetPasswordSetPasswordBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordSetPasswordFragment : Fragment() {
    private val binding by viewBinding(FragmentResetPasswordSetPasswordBinding::bind)
    private val viewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password_set_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.continueBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                // TODO navigate next
                requireContext().showToast("ok")
            }
        }
    }


    private fun chekIfFormIsValid(): Boolean {
        var formIsValid = true

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
                binding.passwordSetField.error = getString(R.string.error_wromg_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {}
        }

        when (viewModel.validatePassword(passwordConfirmation)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.passwordConfirmField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.passwordConfirmField.error = getString(R.string.error_wromg_password_format)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {}
        }

        return formIsValid
    }

}