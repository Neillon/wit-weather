package br.com.neillon.location.ui.turnonlocation

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.neillon.location.databinding.FragmentTurnOnLocationBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TurnOnLocationFragment : Fragment() {

    companion object {
        private const val REQUEST_CHECK_SETTINGS: Int = 1
    }

    private var _binding: FragmentTurnOnLocationBinding? = null
    private val binding by lazy { _binding!! }

    private val navController by lazy { findNavController() }

    private val isLocationActivated by lazy {
        try {
            val locationMode =
                Settings.Secure.getInt(context?.contentResolver, Settings.Secure.LOCATION_MODE);
            locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } catch (e: Settings.SettingNotFoundException) {
            false
        }
    }

    private var task: Task<LocationSettingsResponse>? = null
    private lateinit var client: SettingsClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTurnOnLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isLocationActivated) {
            navigateToGetCurrentLocation()
        }

        requestLocationActivation()
    }

    private fun requestLocationActivation() {
        binding.progressBar.isVisible = true
        createLocationRequest()
    }

    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        client = LocationServices.getSettingsClient(requireActivity())
        task = client.checkLocationSettings(builder.build())

        task?.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
            }
        }
    }

    private fun navigateToGetCurrentLocation() {
        Log.i("Neillon", "Passou dentro do navigateToGetCurrentLocation")
        val action =
            TurnOnLocationFragmentDirections.actionTurnOnLocationFragmentToGetCurrentLocationFragment2()
        navController.navigate(action)
    }
}