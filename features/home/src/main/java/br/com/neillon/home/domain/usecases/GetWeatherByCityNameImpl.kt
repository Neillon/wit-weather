package br.com.neillon.home.domain.usecases

import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.entities.CityWeather

class GetWeatherByCityNameImpl(
    private val repository: WeatherRepository
) : GetWeatherByCityName {

    override suspend fun execute(param: GetWeatherByCityName.Params): Either<CityWeather?, Exception> {
        return repository.getWeatherByCityName(param.cityName)
    }
}