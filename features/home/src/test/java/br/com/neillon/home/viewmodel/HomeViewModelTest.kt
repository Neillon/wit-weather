package br.com.neillon.home.viewmodel

import androidx.lifecycle.Observer
import br.com.neillon.core.common.Either
import br.com.neillon.core.extensions.EMPTY
import br.com.neillon.home.base.BaseCoroutineTest
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameUseCase
import br.com.neillon.home.domain.usecases.GetWeatherByLatLongUseCase
import br.com.neillon.home.mock.Mocks
import br.com.neillon.home.presentation.mapper.CityWeatherUiMapper
import br.com.neillon.home.presentation.ui.HomeViewModel
import br.com.neillon.home.presentation.ui.viewstate.HomeViewEvent
import br.com.neillon.home.presentation.ui.viewstate.HomeViewState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest : BaseCoroutineTest() {

    @MockK(relaxed = true)
    private lateinit var getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase

    @MockK(relaxed = true)
    private lateinit var getWeatherByLatLongUseCase: GetWeatherByLatLongUseCase

    @MockK(relaxed = true)
    private lateinit var mapper: CityWeatherUiMapper

    @MockK(relaxed = true)
    private lateinit var viewStateObserver: Observer<HomeViewState>

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = HomeViewModel(
            getWeatherByLatLongUseCase,
            getWeatherByCityNameUseCase,
            mapper
        )

        every { mapper.map(any()) } returns Mocks.Weather.WEATHER_UI
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun `should update ViewState with data for event GetWeatherByCityName`() =
        testCoroutineRule.runBlockingTest {
            coEvery { getWeatherByCityNameUseCase.execute(any()) } returns Either.value(Mocks.Weather.WEATHER)

            viewModel.processEvent(HomeViewEvent.GetWeatherByCityName(String.EMPTY))

            val expectedViewState = buildDataViewState()

            verify(exactly = 1) {
                viewStateObserver.onChanged(HomeViewState())
                viewStateObserver.onChanged(buildLoadingViewState())
                viewStateObserver.onChanged(expectedViewState)
            }
        }

    @Test
    fun `should update ViewState with error when GetWeatherByCityNameUseCase returns error`() =
        testCoroutineRule.runBlockingTest {
            val exception = Exception("Error")
            coEvery { getWeatherByCityNameUseCase.execute(any()) } returns Either.error(exception)

            viewModel.processEvent(HomeViewEvent.GetWeatherByCityName(String.EMPTY))

            val expectedViewState = buildErrorViewState(exception)

            verify(exactly = 1) {
                viewStateObserver.onChanged(HomeViewState())
                viewStateObserver.onChanged(buildLoadingViewState())
                viewStateObserver.onChanged(expectedViewState)
            }
        }

    @Test
    fun `should update ViewState with data for event GetWeatherByLatLong`() =
        testCoroutineRule.runBlockingTest {
            coEvery { getWeatherByLatLongUseCase.execute(any()) } returns Either.value(Mocks.Weather.WEATHER)

            viewModel.processEvent(
                HomeViewEvent.GetWeatherByLatLong(
                    Double.MAX_VALUE,
                    Double.MAX_VALUE
                )
            )

            val expectedViewState = buildDataViewState()

            verify(exactly = 1) {
                viewStateObserver.onChanged(HomeViewState())
                viewStateObserver.onChanged(buildLoadingViewState())
                viewStateObserver.onChanged(expectedViewState)
            }
        }

    @Test
    fun `should update ViewState with error when GetWeatherByLatLongUseCase returns error`() =
        testCoroutineRule.runBlockingTest {
            val exception = Exception("Error")
            coEvery { getWeatherByLatLongUseCase.execute(any()) } returns Either.error(exception)

            viewModel.processEvent(
                HomeViewEvent.GetWeatherByLatLong(
                    Double.MAX_VALUE,
                    Double.MAX_VALUE
                )
            )

            val expectedViewState = buildErrorViewState(exception)

            verify(exactly = 1) {
                viewStateObserver.onChanged(HomeViewState())
                viewStateObserver.onChanged(buildLoadingViewState())
                viewStateObserver.onChanged(expectedViewState)
            }
        }

    private fun buildLoadingViewState() = HomeViewState(
        isLoading = true,
        hasError = false,
        error = null,
        currentLocation = null
    )

    private fun buildDataViewState() = HomeViewState(
        isLoading = false,
        hasError = false,
        error = null,
        currentLocation = Mocks.Weather.WEATHER_UI
    )

    private fun buildErrorViewState(e: Exception) = HomeViewState(
        isLoading = false,
        hasError = true,
        error = e.message,
        currentLocation = null
    )
}
