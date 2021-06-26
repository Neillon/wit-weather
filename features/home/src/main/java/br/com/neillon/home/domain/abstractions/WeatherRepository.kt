package br.com.neillon.home.domain.abstractions

import br.com.neillon.core.abstractions.Repository
import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.entities.CityWeather

interface WeatherRepository : Repository<CityWeather> {
    suspend fun getWeatherByCityName(cityName: String): Either<CityWeather?, Exception>
    suspend fun getWeatherByLatLong(
        latitude: Double,
        longitude: Double
    ): Either<CityWeather?, Exception>
}