package br.com.neillon.home.domain.usecases

import br.com.neillon.core.abstractions.UseCase
import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.entities.CityWeather

interface GetWeatherByCityNameUseCase :
    UseCase<GetWeatherByCityNameUseCase.Params, Either<CityWeather?, Exception>> {
    data class Params(val cityName: String)
}