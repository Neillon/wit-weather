package br.com.neillon.home.presentation.ui.viewstate

import br.com.neillon.home.presentation.dto.CityWeatherUi

data class HomeViewState(
    val currentLocation: CityWeatherUi? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val error: String? = null
)