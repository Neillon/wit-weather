package br.com.neillon.home.domain.usecases

import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.entities.CityWeather

class GetWeatherByLatLongImpl(
    private val repository: WeatherRepository
) : GetWeatherByLatLong {
    override suspend fun execute(param: GetWeatherByLatLong.Params): Either<CityWeather?, Exception> {
        return repository.getWeatherByLatLong(
            latitude = param.latitude,
            longitude = param.longitude
        )
    }
}