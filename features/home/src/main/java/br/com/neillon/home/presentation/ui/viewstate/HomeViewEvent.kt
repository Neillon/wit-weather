package br.com.neillon.home.presentation.ui.viewstate

sealed class HomeViewEvent {
    data class GetWeatherByCityName(val cityName: String) : HomeViewEvent()
    data class GetWeatherByLatLong(val latitude: Double, val longitude: Double) : HomeViewEvent()
}