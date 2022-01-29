package by.alexandr7035.affinidi_id.presentation.error_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentErrorBinding
import by.kirich1409.viewbindingdelegate.viewBinding


class ErrorFragment : Fragment() {
    private val args by navArgs<ErrorFragmentArgs>()
    private val binding by viewBinding(FragmentErrorBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.errorTitle.text = args.title
        binding.errorMessage.text = args.message

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}