package br.com.neillon.location.ui.start

import android.Manifest
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.neillon.core.androidextensions.checkPermissions
import br.com.neillon.location.databinding.FragmentLocationStartBinding

class LocationStartFragment : Fragment() {

    companion object {
        private const val TAG = "LocationStartFragment"
        private const val REQUEST_CHECK_SETTINGS: Int = 1
    }

    private var _binding: FragmentLocationStartBinding? = null
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
    private val hasLocationPermissions by lazy {
        requireContext().checkPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hasLocationPermissions && isLocationActivated) {
            navigateToGetCurrentLocation()
            return
        }

        navigateToAskForLocationPermissions()
    }

    private fun navigateToAskForLocationPermissions() {
        val action =
            LocationStartFragmentDirections.actionLocationStartFragmentToAskForPermissionFragment()
        navController.navigate(action)
    }

    private fun navigateToGetCurrentLocation() {
        val action =
            LocationStartFragmentDirections.actionLocationStartFragmentToGetCurrentLocationFragment()
        navController.navigate(action)
    }
}