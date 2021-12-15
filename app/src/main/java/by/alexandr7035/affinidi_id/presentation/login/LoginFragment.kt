package by.alexandr7035.affinidi_id.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.data.SignUpModel
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
            if (chekIfFormIsValid()) {
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
            when (response) {
                is SignInModel.Success -> {
                    Toast.makeText(requireContext(), response.userDid, Toast.LENGTH_LONG).show()
                }
                is SignInModel.Fail -> {
                    Toast.makeText(requireContext(), response.errorType.name, Toast.LENGTH_LONG).show()
                }
            }
        })
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