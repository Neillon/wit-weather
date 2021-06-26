package br.com.neillon.home.usecase

import br.com.neillon.core.common.Either
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.usecases.GetWeatherByCityName
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameImpl
import br.com.neillon.home.mock.Mocks
import br.com.neillon.home.rules.TestCoroutineRule
import br.com.neillon.network.Constants
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetWeatherByCityNameTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherByCityName

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetWeatherByCityNameImpl(repository)
    }

    @Test
    fun `When call GetWeatherByCityName should return value with success`() =
        testCoroutineRule.runBlockingTest {
            val expected = Either.value(Mocks.Weather.WEATHER)
            coEvery { repository.getWeatherByCityName(any()) } returns expected

            val params = GetWeatherByCityName.Params(cityName = "Lisbon")
            val result = useCase.execute(params)

            assertEquals(expected, result)
        }

    @Test
    fun `When call GetWeatherByCityNameImpl and repository returns an error, should return error`() =
        testCoroutineRule.runBlockingTest {
            val exception = Exception(Constants.Network.Exceptions.INVALID_API_KEY_STATUS_MESSAGE)
            val expected = Either.error(exception)
            coEvery { repository.getWeatherByCityName(any()) } returns expected

            val params = GetWeatherByCityName.Params(cityName = "Lisbon")
            val result = useCase.execute(params)

            assertEquals(expected, result)
        }
}