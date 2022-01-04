package by.alexandr7035.affinidi_id.presentation.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializePasswordResetModel
import by.alexandr7035.affinidi_id.databinding.FragmentResetPasswordSetUsernameBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ResetPasswordSetUsernameFragment : Fragment() {

    private val binding by viewBinding(FragmentResetPasswordSetUsernameBinding::bind)
    private val viewModel by navGraphViewModels<ResetPasswordViewModel>(R.id.resetPasswordGraph) { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password_set_username, container, false)
    }

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
            if (chekIfFormIsValid()) {
                binding.progressView.isVisible = true

                val userName = binding.userNameEditText.text.toString().lowercase(Locale.getDefault())
                viewModel.initializePasswordReset(userName)
            }
        }

        viewModel.initializePasswordResetLiveData.observe(viewLifecycleOwner, { result ->
            binding.progressView.isVisible = false

            when (result) {
                is InitializePasswordResetModel.Success -> {
                    findNavController().navigateSafe(ResetPasswordSetUsernameFragmentDirections
                        .actionResetPasswordSetUsernameFragmentToResetPasswordSetPasswordFragment(result.userName))
                }

                is InitializePasswordResetModel.Fail -> {
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

        return formIsValid
    }

}