package by.alexandr7035.affinidi_id.presentation.registration

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationConfirmationBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import hideKeyboard


@AndroidEntryPoint
class RegistrationConfirmationFragment : BottomSheetDialogFragment(R.layout.fragment_registration_confirmation) {

    private val viewModel by navGraphViewModels<RegistrationViewModel>(R.id.signUpGraph) { defaultViewModelProviderFactory }
    private val binding by viewBinding(FragmentRegistrationConfirmationBinding::bind)
    private val safeArgs by navArgs<RegistrationConfirmationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmBtn.setOnClickListener {

            if (checkIfFormIsValid()) {
                hideKeyboard()

                binding.progressView.root.isVisible = true

                viewModel.confirmSignUp(
                    confirmationCode = binding.confirmationCodeEditText.text.toString(),
                    confirmationToken = safeArgs.confirmSignUpToken,
                    userName = safeArgs.userName
                )

                dialog?.dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.signUpConfirmationLiveData.postValue(ConfirmSignUpResponseModel.Fail(ErrorType.CONFIRMATION_CODE_DIALOG_DISMISSED))
    }


    private fun checkIfFormIsValid(): Boolean {
        var formIsValid = true

        val code = binding.confirmationCodeEditText.text.toString()
        when (viewModel.validateConfirmationCode(code)) {
            InputValidationResult.EMPTY_FIELD -> {
                binding.confirmationCodeField.error = getString(R.string.error_empty_field)
                formIsValid = false
            }

            else -> {}
        }

        return formIsValid
    }
}