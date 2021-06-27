package br.com.neillon.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.neillon.home.R
import br.com.neillon.home.databinding.FragmentHomeBinding
import br.com.neillon.home.presentation.dto.CityWeatherUi
import br.com.neillon.home.presentation.extensions.toAtmosphericPressure
import br.com.neillon.home.presentation.extensions.toCelsius
import br.com.neillon.home.presentation.extensions.toPercentageString
import br.com.neillon.home.presentation.extensions.toWindVelocity
import br.com.neillon.home.presentation.ui.cityselection.CitySelectionBottomSheetFragment
import br.com.neillon.home.presentation.ui.viewstate.HomeViewEffect
import br.com.neillon.home.presentation.ui.viewstate.HomeViewEvent
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding by lazy { _binding!! }

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        observeViewState()
        observeViewEffect()
    }

    private fun observeViewState() {
        homeViewModel.viewState.observe(viewLifecycleOwner, { state ->
            Log.i(TAG, "Update state to $state")
            if (state.isLoading) {
                showLoading()
                return@observe
            }

            if (state.hasError) {
                showError(state.error)
                showEmptyLocation()
                return@observe
            }

            if (state.currentLocation != null) {
                showCurrentLocation(state.currentLocation)
            } else {
                showEmptyLocation()
            }
        })
    }

    private fun showLoading() {
        binding.containerData.isVisible = false
        binding.containerEmptyState.isVisible = false
        binding.textViewLoading.isVisible = true
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.textViewLoading.isVisible = false
        binding.progressBar.isVisible = false
    }

    private fun showError(message: String?) {
        hideLoading()
        hideEmptyStateContainer()

        Toast.makeText(
            requireContext(),
            message ?: getString(R.string.unknow_error),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun hideEmptyStateContainer() {
        binding.containerEmptyState.isVisible = false
    }

    private fun showEmptyLocation() {
        binding.apply {
            containerData.isVisible = false
            containerEmptyState.isVisible = true
        }
    }

    private fun observeViewEffect() {
        homeViewModel.viewEffect.observe(viewLifecycleOwner, { effect ->
            when (effect) {
                is HomeViewEffect.UpdateCurrentLocation -> showCurrentLocation(effect.currentLocation)
            }
        })
    }

    private fun showCurrentLocation(currentLocation: CityWeatherUi) {
        hideLoading()
        hideEmptyStateContainer()
        binding.containerData.isVisible = true
        binding.apply {
            textViewCity.text = currentLocation.cityName
            textViewTemperature.text = currentLocation.temperature.toCelsius()
            textViewMaxTemp.text = currentLocation.max.toCelsius()
            textViewMinTemp.text = currentLocation.min.toCelsius()
            textViewWindVelocity.text = currentLocation.windVelocity.toWindVelocity()
            textViewHumidity.text = currentLocation.humidity.toPercentageString()
            textViewPressure.text = currentLocation.pressure.toAtmosphericPressure()

            textViewDescription.text = currentLocation.description.capitalize()

            imageViewDescription.setImageResource(
                when {
                    currentLocation.description.toLowerCase()
                        .contains("clear") -> R.drawable.ic_clear_sky
                    currentLocation.description.toLowerCase()
                        .contains("rain") -> R.drawable.ic_heavy_rain
                    else -> R.drawable.ic_clouds
                }
            )


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        binding.fabCitySelection.setOnClickListener { openCitySelectionBottomSheet() }
    }

    private fun openCitySelectionBottomSheet() {
        CitySelectionBottomSheetFragment.newInstance { cityName ->
            homeViewModel.processEvent(HomeViewEvent.GetWeatherByCityName(cityName))

            Log.i(TAG, "Should update the current location to $cityName")
        }.show(parentFragmentManager, CitySelectionBottomSheetFragment.TAG)
    }
}
