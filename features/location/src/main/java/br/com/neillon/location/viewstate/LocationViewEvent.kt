package br.com.neillon.location.viewstate

sealed class LocationViewEvent {
    object LoadCurrentLocation : LocationViewEvent()
    data class UpdateCurrentLocation(
        var latitude: Double,
        var longitude: Double
    ) : LocationViewEvent()
}