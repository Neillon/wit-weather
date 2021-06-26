package br.com.neillon.home.repository

import br.com.neillon.core.common.Either
import br.com.neillon.home.data.api.OpenWeatherApi
import br.com.neillon.home.data.mapper.WeatherResponseMapper
import br.com.neillon.home.data.mapper.WeatherResponseMapperImpl
import br.com.neillon.home.data.repositories.WeatherRepositoryImpl
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.mock.ClientApiMock
import br.com.neillon.home.mock.Mocks
import br.com.neillon.network.Constants
import br.com.neillon.network.Constants.Network.GsonDefaults.gsonDefault
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherRepositoryTest {

    companion object {
        const val PATH = "/"
    }

    private lateinit var sut: WeatherRepository
    private lateinit var api: OpenWeatherApi
    private lateinit var server: MockWebServer
    private lateinit var mapper: WeatherResponseMapper

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val url = server.url(PATH).toString()

        api = ClientApiMock(url).createService(OpenWeatherApi::class.java)
        mapper = WeatherResponseMapperImpl()

        sut = WeatherRepositoryImpl(api, mapper)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `When call getWeatherByCityName should return the weather`() {
        runBlocking {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(200)
            mockedResponse.addHeader("content-type: application/json")
            mockedResponse.setBody(gsonDefault.toJson(Mocks.Weather.WEATHER_RESPONSE))

            val result = sut.getWeatherByCityName("Lisboa")
            val expectedResult = Either.value(
                mapper.map(Mocks.Weather.WEATHER_RESPONSE)
            )

            when (result) {
                is Either.Error -> true
                is Either.Value -> assertEquals(expectedResult, result.packet)
            }
        }
    }

    @Test
    fun `When call getWeatherByCityName should return error`() {
        runBlocking {
            val expectedResult =
                Either.Error(Constants.Network.Exceptions.INVALID_API_KEY_STATUS_MESSAGE)

            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(401)
            mockedResponse.addHeader("content-type: application/json")
            mockedResponse.setBody(gsonDefault.toJson(expectedResult))
            server.enqueue(mockedResponse)

            val result = sut.getWeatherByCityName("Lisboa")

            when (result) {
                is Either.Error -> {
                    assertEquals(expectedResult.packet, result.packet.message)
                }
                is Either.Value -> true
            }
        }
    }

    @Test
    fun `When call getWeatherByLatLong should return the weather`() {
        runBlocking {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(200)
            mockedResponse.addHeader("content-type: application/json")
            mockedResponse.setBody(gsonDefault.toJson(Mocks.Weather.WEATHER_RESPONSE))

            val result = sut.getWeatherByLatLong(Double.MIN_VALUE, Double.MAX_VALUE)
            val expectedResult = Either.value(
                mapper.map(Mocks.Weather.WEATHER_RESPONSE)
            )

            when (result) {
                is Either.Error -> true
                is Either.Value -> assertEquals(expectedResult, result.packet)
            }
        }
    }

    @Test
    fun `When call getWeatherByLatLong should return error`() {
        runBlocking {
            val expectedResult =
                Either.Error(Constants.Network.Exceptions.INVALID_API_KEY_STATUS_MESSAGE)

            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(401)
            mockedResponse.addHeader("content-type: application/json")
            mockedResponse.setBody(gsonDefault.toJson(expectedResult))
            server.enqueue(mockedResponse)

            val result = sut.getWeatherByLatLong(Double.MAX_VALUE, Double.MAX_VALUE)

            when (result) {
                is Either.Error -> {
                    assertEquals(expectedResult.packet, result.packet.message)
                }
                is Either.Value -> true
            }
        }
    }
}