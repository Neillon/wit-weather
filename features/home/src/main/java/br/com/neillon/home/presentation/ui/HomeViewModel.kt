package br.com.neillon.home.presentation.ui

import androidx.lifecycle.viewModelScope
import br.com.neillon.core.common.Either
import br.com.neillon.core.common.StateViewModel
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameUseCase
import br.com.neillon.home.domain.usecases.GetWeatherByLatLongUseCase
import br.com.neillon.home.presentation.mapper.CityWeatherUiMapper
import br.com.neillon.home.presentation.ui.viewstate.HomeViewEffect
import br.com.neillon.home.presentation.ui.viewstate.HomeViewEvent
import br.com.neillon.home.presentation.ui.viewstate.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getWeatherByLatLongUseCase: GetWeatherByLatLongUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val mapper: CityWeatherUiMapper
) : StateViewModel<HomeViewState, HomeViewEvent, HomeViewEffect>() {

    init {
        _viewState.value = HomeViewState()
    }

    override fun processEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.GetWeatherByCityName -> getWeatherByCityName(event.cityName)
            is HomeViewEvent.GetWeatherByLatLong -> getWeatherByLatLong(
                event.latitude,
                event.longitude
            )
        }
    }

    private fun getWeatherByCityName(cityName: String) {
        updateStateWithLoading()

        viewModelScope.launch(Dispatchers.IO) {
            val params = GetWeatherByCityNameUseCase.Params(cityName = cityName)
            val result = getWeatherByCityNameUseCase.execute(params)

            withContext(Dispatchers.Main) {
                when (result) {
                    is Either.Error -> updateStateWithError(result.packet)
                    is Either.Value -> {
                        result.packet?.let { weather ->
                            updateStateWithData(weather)
                        }
                    }
                }
            }
        }
    }

    private fun getWeatherByLatLong(latitude: Double, longitude: Double) {
        updateStateWithLoading()

        viewModelScope.launch {
            val params = GetWeatherByLatLongUseCase.Params(
                latitude = latitude,
                longitude = longitude
            )
            delay(1000L)
            val result = getWeatherByLatLongUseCase.execute(params)

            withContext(Dispatchers.Main) {
                when (result) {
                    is Either.Error -> updateStateWithError(result.packet)
                    is Either.Value -> {
                        result.packet?.let { weather ->
                            updateStateWithData(weather)
                        }
                    }
                }
            }
        }
    }

    private fun updateStateWithData(cityWeather: CityWeather) {
        _viewState.value =
            _viewState.value!!.copy(
                isLoading = false,
                hasError = false,
                error = null,
                currentLocation = mapper.map(cityWeather)
            )
    }

    private fun updateStateWithLoading() {
        _viewState.value = _viewState.value!!.copy(
            isLoading = true,
            hasError = false
        )
    }

    private fun updateStateWithError(error: Exception) {
        _viewState.value =
            _viewState.value!!.copy(
                isLoading = false,
                hasError = true,
                error = error.message
            )
    }
}