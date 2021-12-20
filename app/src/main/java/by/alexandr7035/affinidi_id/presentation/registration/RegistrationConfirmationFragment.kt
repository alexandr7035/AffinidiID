package by.alexandr7035.affinidi_id.presentation.registration

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationConfirmationBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class RegistrationConfirmationFragment : BottomSheetDialogFragment() {

    private val viewModel by navGraphViewModels<RegistrationViewModel>(R.id.signUpGraph) { defaultViewModelProviderFactory }
    private val binding by viewBinding(FragmentRegistrationConfirmationBinding::bind)
    private val args by navArgs<RegistrationConfirmationFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmBtn.setOnClickListener {
            // TODO validation
            if (chekIfFormIsValid()) {
                binding.progressView.isVisible = true

                viewModel.confirmSignUp(
                    confirmationCode = binding.confirmationCodeEditText.text.toString(),
                    confirmationToken = args.confirmSignUpToken
                )

                dialog?.dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.signUpConfirmationLiveData.postValue(SignUpConfirmationModel.Fail(ErrorType.CONFIRMATION_CODE_DIALOG_DISMISSED))
    }

    private fun chekIfFormIsValid(): Boolean {
        // TODO validation
        return true
    }
}