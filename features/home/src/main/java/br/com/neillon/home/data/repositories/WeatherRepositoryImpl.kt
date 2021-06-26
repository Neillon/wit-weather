package br.com.neillon.home.data.repositories

import br.com.neillon.core.common.Either
import br.com.neillon.home.data.api.OpenWeatherApi
import br.com.neillon.home.data.mapper.WeatherResponseMapper
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.network.manager.NetworkManager

class WeatherRepositoryImpl(
    private val api: OpenWeatherApi,
    private val mapper: WeatherResponseMapper
) : WeatherRepository {
    override suspend fun getWeatherByCityName(
        cityName: String
    ): Either<CityWeather?, Exception> =
        try {
            val result = NetworkManager.doAsyncRequest {
                api.getWeatherFromCity(cityName)
            }

            Either.value(mapper.map(result))
        } catch (e: Exception) {
            Either.error(e)
        }

    override suspend fun getWeatherByLatLong(
        latitude: Double,
        longitude: Double
    ): Either<CityWeather?, Exception> =
        try {
            val result = NetworkManager.doAsyncRequest {
                api.getWeatherFromLatLong(latitude, longitude)
            }

            Either.value(mapper.map(result))
        } catch (e: Exception) {
            Either.error(e)
        }

}