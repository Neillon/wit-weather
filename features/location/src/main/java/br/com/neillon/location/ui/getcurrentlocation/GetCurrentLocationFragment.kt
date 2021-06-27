package br.com.neillon.location.ui.getcurrentlocation

import android.annotation.SuppressLint
import android.app.Service
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.neillon.core.navigation.Features
import br.com.neillon.core.navigation.Navigator
import br.com.neillon.location.databinding.FragmentGetCurrentLocationBinding
import org.koin.android.ext.android.inject

class GetCurrentLocationFragment : Fragment(), LocationListener {

    private var _binding: FragmentGetCurrentLocationBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator by inject()

    private val locationManager by lazy { requireContext().getSystemService(Service.LOCATION_SERVICE) as LocationManager }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetCurrentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLocationChanged(location: Location) {
        navigateToHomeWithLocation(location)
    }

    private fun navigateToHomeWithLocation(lastKnownLocation: Location) {
        locationManager.removeUpdates(this)
        navigator.navigateToFeature(
            requireContext(),
            Features.Home(currentLocation = lastKnownLocation)
        )
        requireActivity().finish()
    }
}