package br.com.neillon.home.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.neillon.core.extensions.ZERO
import br.com.neillon.home.databinding.ActivityHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HomeActivity"

        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"

        fun newInstance(context: Context, latitude: Double, longitude: Double) =
            Intent(context, HomeActivity::class.java).apply {
                putExtra(LATITUDE, latitude)
                putExtra(LONGITUDE, longitude)
            }
    }

    private var _binding: ActivityHomeBinding? = null
    private val binding by lazy { _binding!! }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(binding.homeNavHostFragment.id) as NavHostFragment?
    }
    private lateinit var navController: NavController

    private val homeViewModel: HomeViewModel by viewModel()

    private var latitude: Double = Double.ZERO
        get() = intent.getDoubleExtra(LATITUDE, Double.ZERO)
    private var longitude: Double = Double.ZERO
        get() = intent.getDoubleExtra(LONGITUDE, Double.ZERO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(TAG, "onCreate: Position: [$latitude : $longitude]")
        setupViews()
    }

    private fun setupViews() {
        setupNavigation()
    }

    private fun setupNavigation() {
        navHostFragment?.let {
            navController = it.findNavController()
            binding.homeToolbar.setupWithNavController(navController)
        }
    }
}