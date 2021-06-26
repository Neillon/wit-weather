package br.com.neillon.home.mock

import br.com.neillon.core.extensions.EMPTY
import br.com.neillon.home.data.dto.*
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.entities.Weather
import br.com.neillon.home.presentation.dto.CityWeatherUi

object Mocks {
    object Weather {
        val WEATHER_UI = CityWeatherUi(
            cityName = "Lisbon",
            temperature = Int.MIN_VALUE,
            description = "Clear",
            max = Int.MAX_VALUE,
            min = Int.MIN_VALUE,
            windVelocity = Double.MAX_VALUE,
            humidity = Int.MIN_VALUE,
            pressure = Int.MAX_VALUE
        )
        val WEATHER = CityWeather(
            name = "Lisbon",
            longitude = Double.MAX_VALUE,
            latitude = Double.MIN_VALUE,
            weather = Weather(
                description = String.EMPTY,
                temperature = Int.MIN_VALUE,
                feelsLikeTemperature = Int.MAX_VALUE,
                min = Int.MIN_VALUE,
                max = Int.MAX_VALUE,
                pressure = Int.MIN_VALUE,
                humidity = Int.MIN_VALUE,
                windSpeed = Double.MAX_VALUE,
            )
        )

        val WEATHER_RESPONSE = CityWeatherResponse(
            coordinates = CityCoordinatesResponse(
                latitude = Double.MAX_VALUE,
                longitude = Double.MIN_VALUE
            ),
            weather = listOf(
                WeatherResponse(
                    id = Int.MIN_VALUE,
                    main = "Clear",
                    description = "Clear sky",
                    icon = "1d0"
                )
            ),
            base = "base",
            main = WeatherDetailsResponse(
                temp = Double.MIN_VALUE,
                feelsLike = Double.MAX_VALUE,
                min = Double.MIN_VALUE,
                max = Double.MAX_VALUE,
                pressure = Int.MIN_VALUE,
                humidity = Int.MAX_VALUE
            ),
            visibility = Int.MAX_VALUE,
            wind = WindResponse(
                speed = Double.MAX_VALUE,
                deg = Int.MIN_VALUE,
                gust = Double.MIN_VALUE
            ),
            dt = Long.MIN_VALUE,
            name = "Lisbon",
        )
    }
}