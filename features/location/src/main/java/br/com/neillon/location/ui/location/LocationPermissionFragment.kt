package br.com.neillon.location.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.neillon.core.entities.GeoLocation
import br.com.neillon.core.navigation.Features
import br.com.neillon.core.navigation.Navigator
import br.com.neillon.location.databinding.FragmentLocationPermissionBinding
import br.com.neillon.location.ui.location.viewstate.LocationViewEffect
import br.com.neillon.location.ui.location.viewstate.LocationViewEvent
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject

class LocationPermissionFragment : Fragment() {

    companion object {
        private const val TAG = "LocationPermission"
    }

    private var _binding: FragmentLocationPermissionBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator by inject()
    private val viewModel by viewModels<LocationPermissionViewModel>()

    private val hasLocationPermission by lazy {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        hasFineLocationPermission && hasCoarseLocationPermission
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            listenLocationUpdates()
            viewModel.processEvent(LocationViewEvent.LoadCurrentLocation)
        } else {
            requireActivity().finish()
        }
    }
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.viewEffect.observe(viewLifecycleOwner, {
            when (it) {
                is LocationViewEffect.GoToHome -> navigateToHomeWithLocation(it.currentLocation)
            }
        })
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, {
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
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun navigateToHomeWithLocation(currentLocation: GeoLocation) {
        navigator.navigateToFeature(requireContext(), Features.Home(currentLocation))
        requireActivity().finish()
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
                    "listenLocationUpdates: Current Location [${location.latitude}, ${location.longitude}]"
                )
            }
            .addOnFailureListener {
                Log.i(TAG, "listenLocationUpdates: Error trying to get the current location")
            }
    }
}