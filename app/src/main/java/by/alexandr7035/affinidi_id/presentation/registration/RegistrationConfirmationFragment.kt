package by.alexandr7035.affinidi_id.presentation.registration

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
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.clearError
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showToast
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationConfirmationBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class RegistrationConfirmationFragment : Fragment() {

    private val viewModel by viewModels<RegistrationViewModel>()
    private val binding by viewBinding(FragmentRegistrationConfirmationBinding::bind)
    private val args by navArgs<RegistrationConfirmationFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.debug(args.confirmSignUpToken)

        binding.confirmationCodeEditText.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.confirmationCodeField.clearError()
            }
        }

        viewModel.signUpConfirmationLiveData.observe(viewLifecycleOwner, { signUpConfirmationResult ->
            binding.progressView.isVisible = false

            when (signUpConfirmationResult) {
                is SignUpConfirmationModel.Success -> {
                    requireContext().showToast("Successful registration")
                    findNavController().navigateSafe(RegistrationConfirmationFragmentDirections.actionRegistrationConfirmationFragmentToProfileFragment())
                }
                is SignUpConfirmationModel.Fail -> {
                    when (signUpConfirmationResult.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            requireContext().showToast(getString(R.string.error_failed_connection))
                        }
                        ErrorType.WRONG_CONFIRMATION_CODE -> {
                            binding.confirmationCodeField.error = getString(R.string.error_wrong_confirmation_code)
                        }
                        else -> {
                            requireContext().showToast(getString(R.string.error_unknown))
                        }
                    }
                }
            }
        })


        binding.confirmBtn.setOnClickListener {
            if (chekIfFormIsValid()) {
                binding.progressView.isVisible = true

                viewModel.confirmSignUp(
                    confirmationCode = binding.confirmationCodeEditText.text.toString(),
                    confirmationToken = args.confirmSignUpToken
                )
            }
        }
    }

    private fun chekIfFormIsValid(): Boolean {
        // TODO validation
        return true
    }
}