package by.alexandr7035.affinidi_id.presentation.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showErrorDialog
import by.alexandr7035.affinidi_id.databinding.FragmentLogoutBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentLogoutBinding::bind)
    private val viewModel by viewModels<LogoutViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.logOutLiveData.observe(viewLifecycleOwner) { logout ->

            binding.progressView.root.isVisible = false

            when (logout) {
                is LogOutModel.Success -> {
                    findNavController().navigateSafe(LogoutFragmentDirections.actionLogoutFragmentToLoginFragment())
                }
                is LogOutModel.Fail -> {
                    when (logout.errorType) {
                        ErrorType.FAILED_CONNECTION -> {
                            showErrorDialog(
                                getString(R.string.error_failed_connection_title),
                                getString(R.string.error_failed_connection)
                            )
                        }
                        // Including UNKNOWN_ERROR
                        else -> {
                            showErrorDialog(
                                getString(R.string.error_unknown_title),
                                getString(R.string.error_unknown)
                            )
                        }
                    }
                }
            }
        }

        binding.logoutBtn.setOnClickListener {
            binding.progressView.root.isVisible = true
            viewModel.logOut()
        }
    }

}