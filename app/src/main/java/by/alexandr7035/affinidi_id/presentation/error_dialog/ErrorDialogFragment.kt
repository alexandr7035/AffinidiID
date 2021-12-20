package by.alexandr7035.affinidi_id.presentation.error_dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.databinding.FragmentErrorDialogBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ErrorDialogFragment : BottomSheetDialogFragment() {
    private val args by navArgs<ErrorDialogFragmentArgs>()
    private val binding by viewBinding(FragmentErrorDialogBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorTitle.text = args.title
        binding.errorMessage.text = args.message

        binding.closeBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }
}