package by.alexandr7035.affinidi_id.presentation.credential_raw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialRawBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import android.text.method.ScrollingMovementMethod





class CredentialRawFragment : Fragment() {

    private val binding by viewBinding(FragmentCredentialRawBinding::bind)
    private val safeArgs by navArgs<CredentialRawFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential_raw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }



        binding.jsonTextView.movementMethod = ScrollingMovementMethod()

        binding.jsonTextView.text = safeArgs.rawCredential
    }
}