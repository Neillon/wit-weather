package br.com.neillon.home.presentation.mapper

import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.presentation.dto.CityWeatherUi

class CityWeatherUiMapperImpl : CityWeatherUiMapper {
    override fun map(item: CityWeather): CityWeatherUi =
        CityWeatherUi(
            cityName = item.name,
            temperature = item.weather.temperature,
            description = item.weather.description,
            max = item.weather.max,
            min = item.weather.min,
            windVelocity = item.weather.windSpeed,
            humidity = item.weather.humidity,
            pressure = item.weather.pressure,
        )
}