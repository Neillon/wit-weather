package br.com.neillon.core.navigation

import br.com.neillon.core.entities.GeoLocation

sealed class Features {
    data class Home(val currentLocation: GeoLocation) : Features()
    object Location : Features()
}
