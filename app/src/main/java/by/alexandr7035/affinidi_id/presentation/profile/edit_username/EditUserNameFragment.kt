package by.alexandr7035.affinidi_id.presentation.profile.edit_username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameModel
import by.alexandr7035.affinidi_id.databinding.FragmentEditUserNameBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditUserNameFragment : Fragment() {

    private val binding by viewBinding(FragmentEditUserNameBinding::bind)
    private val viewModel by viewModels<EditUserNameViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.newUserNameEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.newUserNameField.clearError()
            }
        }

        binding.continueBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.progressView.isVisible = true

                val userName = binding.newUserNameEditText.text.toString()
                    .lowercase(Locale.getDefault())

                viewModel.changeUserName(userName)
            }
        }


        viewModel.editUserNameLiveData.observe(viewLifecycleOwner, { result ->
            binding.progressView.isVisible = false

            when (result) {
                is ChangeUserNameModel.Success -> {
                    findNavController().navigateSafe(
                        EditUserNameFragmentDirections
                            .actionEditUserNameFragmentToEditUserNameConfirmationFragment(result.newUserName)
                    )
                }

                is ChangeUserNameModel.Fail -> {
                    when (result.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }

                        ErrorType.USER_ALREADY_EXISTS -> {
                            binding.newUserNameField.error = getString(R.string.error_username_is_taken)
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

        val userName = binding.newUserNameEditText.text.toString()
        when (viewModel.validateUserName(userName)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.newUserNameField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            InputValidationResult.WRONG_FORMAT -> {
                binding.newUserNameField.error = getString(R.string.error_invalid_user_name)
                formIsValid = false
            }

            InputValidationResult.NO_ERRORS -> {
            }
        }

        return formIsValid
    }
}