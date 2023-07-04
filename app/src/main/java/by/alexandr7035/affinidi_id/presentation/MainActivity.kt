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
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.core.extensions.showSnackBar
import by.alexandr7035.affinidi_id.databinding.ActivityMainBinding
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.profile.ProfileFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getAuthCheckObservable().observe(this) { authCheckResult ->
            binding.progressView.root.isVisible = false

            when (authCheckResult) {
                is GenericRes.Success -> {
                    // Means token did not expired
                    checkBiometricAuth()
                }

                // When fail, it depends.
                is GenericRes.Fail -> {
                    when (authCheckResult.errorType) {
                        // No connection with the internet
                        // But we can let the user inside as some of his data is cached
                        ErrorType.FAILED_CONNECTION -> {
                            checkBiometricAuth()
                        }

                        // User is not logged in
                        ErrorType.NOT_AUTHORIZED -> {
                            navController.navigateSafe(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                        }

                        // That means token has expired
                        // Stay on login fragment and show error message
                        ErrorType.AUTH_SESSION_EXPIRED -> {
                            binding.root.showSnackBar(
                                getString(R.string.error_session_expired), SnackBarMode.Negative, Snackbar.LENGTH_LONG
                            )

                            navController.navigateSafe(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                        }

                        // Unknown error. Show corresponding fragment
                        else -> {
                            navController.navigateSafe(
                                ProfileFragmentDirections.actionGlobalErrorFragment(
                                    getString(R.string.error_unknown_title), getString(R.string.error_unknown)
                                )
                            )
                        }
                    }
                }
            }
        }

        binding.progressView.root.isVisible = true
        viewModel.checkIfAuthorized()
    }


    override fun onResume() {
        super.onResume()
//        checkBiometricAuth()
    }

    private fun checkBiometricAuth() {
        if (viewModel.checkAppLockedWithBiometrics()) {
            if (navController.currentDestination?.id != R.id.biometricsLockFragment) {
                navController.navigateSafe(MainActivityDirections.actionGlobalBiometricsLockFragment())
            }
        }
    }
}