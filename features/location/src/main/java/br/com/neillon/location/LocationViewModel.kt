package br.com.neillon.location

import br.com.neillon.core.common.StateViewModel
import br.com.neillon.core.entities.GeoLocation
import br.com.neillon.location.viewstate.LocationViewEffect
import br.com.neillon.location.viewstate.LocationViewEvent
import br.com.neillon.location.viewstate.LocationViewState

class LocationViewModel :
    StateViewModel<LocationViewState, LocationViewEvent, LocationViewEffect>() {

    init {
        _viewState.value = LocationViewState()
    }

    override fun processEvent(event: LocationViewEvent) {
        when (event) {
            LocationViewEvent.LoadCurrentLocation -> loadLocation()
            is LocationViewEvent.UpdateCurrentLocation -> updateCurrentLocation(
                event.latitude,
                event.longitude
            )
        }
    }

    private fun updateCurrentLocation(latitude: Double, longitude: Double) {
        val currentLocation = GeoLocation(latitude, longitude)
        _viewState.value = _viewState.value!!.copy(
            isLoading = false,
            currentLocation = currentLocation
        )
        _viewEffect.value = LocationViewEffect.GoToHome(currentLocation)
    }

    private fun loadLocation() {
        _viewState.value = _viewState.value!!.copy(isLoading = true)
    }

}