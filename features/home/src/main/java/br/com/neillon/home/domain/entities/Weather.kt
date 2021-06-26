package br.com.neillon.home.domain.entities

data class Weather(
    val description: String,
    var temperature: Int,
    val feelsLikeTemperature: Int,
    var min: Int,
    var max: Int,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double
)
