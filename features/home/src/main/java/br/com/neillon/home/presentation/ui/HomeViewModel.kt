package br.com.neillon.home.presentation.ui

import androidx.lifecycle.ViewModel
import br.com.neillon.home.domain.usecases.GetWeatherByLatLong

class HomeViewModel(
    private val getWeatherByLatLong: GetWeatherByLatLong
) : ViewModel() {

}