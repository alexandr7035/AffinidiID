package by.alexandr7035.affinidi_id.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.databinding.ActivityMainBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.login.LoginFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.navigationBarColor = Color.TRANSPARENT
//        window.statusBarColor = Color.TRANSPARENT
//        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_window))

        // Setting Navigation Controller with the BottomNavigationView )
        binding.bottomNavigationView.setupWithNavController(navController)

        // Primary destinations shown in bottom navigation
        val bottomNavigationDestinations = listOf(
            R.id.profileFragment,
            R.id.credentialsListFragment,
            R.id.scanCredentialQrCodeFragment,
            R.id.mainMenuFragment,
            R.id.logoutFragment
        )

        // Hide bottom navigation for non-primary fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isGone = !bottomNavigationDestinations.contains(destination.id)
        }

        // navigateSafe method allow us not check current destination
        viewModel.getAuthCheckLiveData().observe(this) { authCheckResult ->
            binding.progressView.root.isVisible = false

            when (authCheckResult) {
                is AuthCheckResModel.Success -> {
                    Timber.debug("AUTH_CHECK success")
                    // Means token did not expired
                    navController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                }

                // When fail, it depends.
                is AuthCheckResModel.Fail -> {
                    when (authCheckResult.errorType) {
                        // That means token has expired
                        // Stay on login fragment and show error message
                        ErrorType.AUTHORIZATION_ERROR -> {
                            Timber.debug("AUTH_CHECK expired")
                            binding.root.showSnackBar(
                                getString(R.string.error_session_expired),
                                SnackBarMode.Negative,
                                Snackbar.LENGTH_LONG
                            )

                            // Go to login fragment (if not here)
                            navController.navigateSafe(MainActivityDirections.actionGlobalLoginFragment())
                        }

                        // No connection with the internet
                        // But we can let the user inside as some of his data is cached
                        ErrorType.FAILED_CONNECTION -> {
                            Timber.debug("AUTH_CHECK connection error")
                            navController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
                        }

                        // Unknown error. Show corresponding fragment
                        else -> {
                            navController.navigateSafe(
                                LoginFragmentDirections.actionGlobalErrorFragment(
                                    getString(R.string.error_unknown_title),
                                    getString(R.string.error_unknown)
                                )
                            )
                        }
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Timber.debug("AUTH_CHECK activity onResume")

        // Affinidi token expires in 1 hour
        // So we should check for auth on start
        // and if token expired show login fragment (or stay if already there)
        if (viewModel.checkIfPreviouslyAuthorized()) {
            Timber.debug("AUTH_CHECK start")
            binding.progressView.root.isVisible = true
            viewModel.startAuthCheck()
        }
    }
}