package by.alexandr7035.affinidi_id.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.navigateSafe
import by.alexandr7035.affinidi_id.databinding.ActivityMainBinding
import by.alexandr7035.affinidi_id.presentation.login.LoginFragmentDirections
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

        // Primary destinations shown in bottom navigation
        val bottomNavigationDestinations = listOf(R.id.profileFragment)

        // Hide bottom navigation for non-primary fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isGone = ! bottomNavigationDestinations.contains(destination.id)
        }

        // Go to profile fragment if already authorized
        if (viewModel.checkIfAuthorized()) {
            navController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
        }
    }
}