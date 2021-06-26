package br.com.neillon.home.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.neillon.home.databinding.ActivityHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private var _binding: ActivityHomeBinding? = null
    private val binding by lazy { _binding!! }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(binding.homeNavHostFragment.id) as NavHostFragment?
    }
    private lateinit var navController: NavController

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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