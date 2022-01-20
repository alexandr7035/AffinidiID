package by.alexandr7035.affinidi_id.presentation.credential_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialDetailsBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class CredentialDetailsFragment : Fragment() {

    private val binding by viewBinding(FragmentCredentialDetailsBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}