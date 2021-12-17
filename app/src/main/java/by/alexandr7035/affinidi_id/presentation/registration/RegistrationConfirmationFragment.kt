package by.alexandr7035.affinidi_id.presentation.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentRegistrationConfirmationBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class RegistrationConfirmationFragment : Fragment() {

    private val viewModel by viewModels<AuthViewModel>()
    private val binding by viewBinding(FragmentRegistrationConfirmationBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}