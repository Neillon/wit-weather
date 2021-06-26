package br.com.neillon.home.presentation.ui.viewstate

import br.com.neillon.home.presentation.dto.CityWeatherUi

sealed class HomeViewEffect {
    data class UpdateCurrentLocation(var currentLocation: CityWeatherUi) : HomeViewEffect()
}