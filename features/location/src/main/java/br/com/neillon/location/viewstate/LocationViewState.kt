package br.com.neillon.location.viewstate

import br.com.neillon.core.entities.GeoLocation

data class LocationViewState(
    val isLoading: Boolean = false,
    val currentLocation: GeoLocation? = null
)