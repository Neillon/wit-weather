package br.com.neillon.home.presentation.dto

data class CityWeatherUi(
    val cityName: String,
    val temperature: Int,
    val description: String,
    val max: Int,
    val min: Int,
    val windVelocity: Double,
    val humidity: Int,
    val pressure: Int
)