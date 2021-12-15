package by.alexandr7035.affinidi_id.presentation.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.data.SignUpModel
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationBinding
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

        binding.signUpBtn.setOnClickListener {
            viewModel.signUp("name", "password")
        }


        viewModel.signUpLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is SignUpModel.Success -> {

                }
                is SignUpModel.Fail -> {
                    binding.userNameField.error = it.e.name
                }
            }
        })
    }
}