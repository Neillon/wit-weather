package br.com.neillon.witweather.base

sealed class Features {
    data class Home(val currentLocation: GeoLocation) : Features()
    object Location : Features()
}
