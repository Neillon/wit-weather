package br.com.neillon.location.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.neillon.location.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, LocationActivity::class.java)

        private const val TAG = "LocationActivity"
        private const val REQUEST_CHECK_SETTINGS: Int = 1
    }

    private var _binding: ActivityLocationBinding? = null
    private val binding by lazy { _binding!! }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(binding.locationNavHost.id) as NavHostFragment?
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navController.popBackStack()
    }

    private fun setupNavigation() {
        navHostFragment?.let {
            navController = it.navController
        }
    }
}