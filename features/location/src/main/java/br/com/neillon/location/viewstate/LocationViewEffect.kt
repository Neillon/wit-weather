package br.com.neillon.location.viewstate

import br.com.neillon.core.entities.GeoLocation

sealed class LocationViewEffect {
    data class GoToHome(val currentLocation: GeoLocation) : LocationViewEffect()
}