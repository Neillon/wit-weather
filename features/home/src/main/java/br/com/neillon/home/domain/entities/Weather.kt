package br.com.neillon.home.domain.entities

data class Weather(
    val description: String,
    val temperature: Double,
    val feelsLikeTemperature: Double,
    val min: Double,
    val max: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double
)
