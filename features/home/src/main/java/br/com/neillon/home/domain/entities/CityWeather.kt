package br.com.neillon.home.domain.entities

data class CityWeather(
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val weather: Weather,
)