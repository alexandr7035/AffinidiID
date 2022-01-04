package by.alexandr7035.affinidi_id.presentation.profile.edit_username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.change_username.ConfirmChangeUserNameModel
import by.alexandr7035.affinidi_id.databinding.FragmentEditUserNameConfirmationBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserNameConfirmationFragment : Fragment() {

    private val binding by viewBinding(FragmentEditUserNameConfirmationBinding::bind)
    private val viewModel by viewModels<EditUserNameViewModel>()
    private val safeArgs by navArgs<EditUserNameConfirmationFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_name_confirmation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.confirmBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.progressView.isVisible = true

                val code = binding.confirmationCodeEditText.text.toString()
                viewModel.confirmChangeUserName(
                    newUserName = safeArgs.userName,
                    confirmationCode = code
                )
            }
        }


        viewModel.confirmEditUserNameLiveData.observe(viewLifecycleOwner, { result ->
            binding.progressView.isVisible = false

            when (result) {
                is ConfirmChangeUserNameModel.Success -> {
                    viewModel.saveUserNameToCache(result.newUserName)

                    requireContext().showToast(getString(
                        R.string.successful_username_change,
                        result.newUserName
                    ))

                    // TODO fix profile image url
                    findNavController().navigateSafe(EditUserNameConfirmationFragmentDirections
                        .actionEditUserNameConfirmationFragmentToMainMenuFragment(""))
                }
                is ConfirmChangeUserNameModel.Fail -> {
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