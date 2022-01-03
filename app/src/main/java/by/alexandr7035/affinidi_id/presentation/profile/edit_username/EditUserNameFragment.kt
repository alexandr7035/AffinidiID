package by.alexandr7035.affinidi_id.presentation.profile.edit_username

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentEditUserNameBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

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
                requireContext().showToast("continue")
            }
        }
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

            InputValidationResult.NO_ERRORS -> {}
        }

        return formIsValid
    }
}