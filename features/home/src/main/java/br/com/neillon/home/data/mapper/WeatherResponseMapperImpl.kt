package br.com.neillon.home.data.mapper

import br.com.neillon.core.extensions.EMPTY
import br.com.neillon.home.data.dto.CityWeatherResponse
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.entities.Weather

class WeatherResponseMapperImpl : WeatherResponseMapper {

    override fun map(item: CityWeatherResponse): CityWeather =
        CityWeather(
            name = item.name,
            longitude = item.coordinates.longitude,
            latitude = item.coordinates.latitude,
            weather = Weather(
                description = item.weather?.firstOrNull()?.description ?: String.EMPTY,
                temperature = item.main.temp,
                feelsLikeTemperature = item.main.feelsLike,
                min = item.main.min,
                max = item.main.max,
                pressure = item.main.pressure,
                humidity = item.main.humidity,
                windSpeed = item.wind.speed
            )
        )
}