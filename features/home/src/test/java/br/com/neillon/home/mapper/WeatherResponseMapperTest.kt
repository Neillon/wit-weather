package br.com.neillon.home.mapper

import br.com.neillon.core.extensions.EMPTY
import br.com.neillon.home.data.mapper.WeatherResponseMapper
import br.com.neillon.home.data.mapper.WeatherResponseMapperImpl
import br.com.neillon.home.domain.entities.CityWeather
import br.com.neillon.home.domain.entities.Weather
import br.com.neillon.home.mock.Mocks
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherResponseMapperTest {

    private lateinit var mapper: WeatherResponseMapper

    @Before
    fun setUp() {
        mapper = WeatherResponseMapperImpl()
    }

    @Test
    fun `should map WeatherResponse to domain  with success`() {
        val weatherResponse = Mocks.Weather.WEATHER_RESPONSE
        val expected = CityWeather(
            name = weatherResponse.name,
            longitude = weatherResponse.coordinates.longitude,
            latitude = weatherResponse.coordinates.latitude,
            weather = Weather(
                description = weatherResponse.weather?.first()?.description ?: String.EMPTY,
                temperature = weatherResponse.main.temp.toInt(),
                feelsLikeTemperature = weatherResponse.main.feelsLike.toInt(),
                min = weatherResponse.main.min.toInt(),
                max = weatherResponse.main.max.toInt(),
                pressure = weatherResponse.main.pressure,
                humidity = weatherResponse.main.humidity,
                windSpeed = weatherResponse.wind.speed,
            )
        )

        val result = mapper.map(weatherResponse)

        assertEquals(expected, result)
    }

}