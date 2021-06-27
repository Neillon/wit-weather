package br.com.neillon.core.navigation

sealed class Features {
    data class Home(val currentLocation: android.location.Location) : Features()
    object Location : Features()
}
