package by.alexandr7035.affinidi_id.presentation.credentials_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.databinding.FragmentCredentialsListBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber

class CredentialsListFragment : Fragment() {

    private val binding by viewBinding(FragmentCredentialsListBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credentials_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.debug("onViewCreated creds list")
    }
}