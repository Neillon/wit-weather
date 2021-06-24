package br.com.neillon.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import br.com.neillon.core.entities.GeoLocation
import br.com.neillon.core.navigation.Features
import br.com.neillon.core.navigation.Navigator
import br.com.neillon.location.databinding.ActivityLocationBinding
import br.com.neillon.location.viewstate.LocationViewEffect
import br.com.neillon.location.viewstate.LocationViewEvent
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject

class LocationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LocationActivity"

        fun newInstance(context: Context) = Intent(context, LocationActivity::class.java)
    }

    private var _binding: ActivityLocationBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator by inject()
    private val viewModel by viewModels<LocationViewModel>()

    private val hasLocationPermission by lazy {
        ActivityCompat.checkSelfPermission(
            this, ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            listenLocationUpdates()
            viewModel.processEvent(LocationViewEvent.LoadCurrentLocation)
        } else {
            finish()
        }
    }
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verifyIfUserHasPermission()

        setupViews()

        observeViewState()
        observeViewEffect()
    }

    private fun verifyIfUserHasPermission() {
        if (hasLocationPermission) {
            listenLocationUpdates()
            viewModel.processEvent(LocationViewEvent.LoadCurrentLocation)
        }
    }

    private fun observeViewEffect() {
        viewModel.viewEffect.observe(this, Observer {
            when (it) {
                is LocationViewEffect.GoToHome -> navigateToHomeWithLocation(it.currentLocation)
            }
        })
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer {
            binding.progressBar.isVisible = it.isLoading
            binding.buttonGivePermission.isVisible = !it.isLoading
        })
    }

    private fun setupViews() {
        setupGivePermissionButton()
    }

    private fun setupGivePermissionButton() {
        binding.buttonGivePermission.setOnClickListener {
            askForLocationPermission()
        }
    }

    private fun askForLocationPermission() {
        if (hasLocationPermission) {
            listenLocationUpdates()
            viewModel.processEvent(LocationViewEvent.LoadCurrentLocation)
        } else {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private fun navigateToHomeWithLocation(currentLocation: GeoLocation) {
        navigator.navigateToFeature(this@LocationActivity, Features.Home(currentLocation))
        finish()
    }

    // Added the SupressLint because it is called inside another function
    @SuppressLint("MissingPermission")
    private fun listenLocationUpdates() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                viewModel.processEvent(
                    LocationViewEvent.UpdateCurrentLocation(
                        location.latitude,
                        location.longitude
                    )
                )

                Log.i(
                    TAG,
                    "listenLocationUpdates: Current Location [${location.latitude} ${location.longitude}] "
                )
            }
    }
}