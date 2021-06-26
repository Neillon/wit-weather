package br.com.neillon.home.domain.usecases

import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.extensions.fromKelvinToCelsius

class GetWeatherByCityNameUseCaseImpl(
    private val repository: WeatherRepository
) : GetWeatherByCityNameUseCase {

    override suspend fun execute(param: GetWeatherByCityNameUseCase.Params): Either<CityWeather?, Exception> {
        val result = repository.getWeatherByCityName(param.cityName)

        return when (result) {
            is Either.Error -> result
            is Either.Value -> {
                result.packet?.weather?.let { weather ->
                    val temperatureInCelsius = weather.temperature.fromKelvinToCelsius()
                    val maxInCelsius = weather.max.fromKelvinToCelsius()
                    val minInCelsius = weather.min.fromKelvinToCelsius()

                    weather.temperature = temperatureInCelsius
                    weather.max = maxInCelsius
                    weather.min = minInCelsius
                }
                result
            }
        }
    }
}