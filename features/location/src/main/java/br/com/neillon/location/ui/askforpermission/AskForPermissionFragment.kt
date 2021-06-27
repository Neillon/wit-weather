package br.com.neillon.location.ui.askforpermission

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.neillon.core.androidextensions.checkPermissions
import br.com.neillon.location.databinding.FragmentLocationPermissionBinding

class AskForPermissionFragment : Fragment() {

    private var _binding: FragmentLocationPermissionBinding? = null
    private val binding by lazy { _binding!! }

    private val navController by lazy { findNavController() }

    private val hasLocationPermission by lazy {
        requireContext().checkPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            requireActivity().finish()
            return@registerForActivityResult
        }

        navigateToTurnOnLocation()
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

        if (hasLocationPermission) {
            navigateToTurnOnLocation()
        }

        setupViews()
    }

    private fun setupViews() {
        setupGivePermissionButton()
    }

    private fun setupGivePermissionButton() {
        binding.buttonGivePermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun navigateToTurnOnLocation() {
        val action =
            AskForPermissionFragmentDirections.actionAskForPermissionFragmentToTurnOnLocationFragment()
        navController.navigate(action)
    }
}