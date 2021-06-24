package br.com.neillon.location

import androidx.lifecycle.Observer
import br.com.neillon.core.entities.GeoLocation
import br.com.neillon.location.ui.location.LocationPermissionViewModel
import br.com.neillon.location.ui.location.viewstate.LocationViewEffect
import br.com.neillon.location.ui.location.viewstate.LocationViewEvent
import br.com.neillon.location.ui.location.viewstate.LocationViewState
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationPermissionViewModelTest : BaseCoroutineTest() {

    private lateinit var stateObserver: Observer<LocationViewState>
    private var stateResultList = mutableListOf<LocationViewState>()

    private lateinit var effectObserver: Observer<LocationViewEffect>
    private var effectResultList = mutableListOf<LocationViewEffect>()

    private lateinit var viewModel: LocationPermissionViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = LocationPermissionViewModel()

        stateObserver = spyk(Observer<LocationViewState> {
            stateResultList.add(it)
        })

        effectObserver = spyk(Observer<LocationViewEffect> {
            effectResultList.add(it)
        })

        viewModel.viewState.observeForever(stateObserver)
        viewModel.viewEffect.observeForever(effectObserver)
    }

    @Test
    fun `should load current location given LoadCurrentLocation`() =
        testCoroutineRule.runBlockingTest {
            val mockedResult = mutableListOf<LocationViewState>()
            val mockObserver = spyk(Observer<LocationViewState> {
                mockedResult.add(it)
            })

            viewModel.viewState.observeForever(mockObserver)

            viewModel.processEvent(LocationViewEvent.LoadCurrentLocation)

            verify(exactly = 2) {
                mockObserver.onChanged(any())
            }
            assertEquals(LocationViewState(isLoading = true), mockedResult[1])
        }

    @Test
    fun `should update current location and emit GoToHome ViewEffect when event is UpdateCUrrentLocation`() =
        testCoroutineRule.runBlockingTest {
            val currentLocation = GeoLocation(
                Double.MIN_VALUE, Double.MIN_VALUE
            )

            viewModel.processEvent(
                LocationViewEvent.UpdateCurrentLocation(
                    currentLocation.latitude,
                    currentLocation.longitude
                )
            )

            verify(exactly = 2) {
                stateObserver.onChanged(any())
            }
            assertEquals(
                LocationViewState(
                    isLoading = false, currentLocation = currentLocation
                ), stateResultList[1]
            )

            verify(exactly = 1) {
                effectObserver.onChanged(any())
            }
            assertEquals(LocationViewEffect.GoToHome(currentLocation), effectResultList[0])
        }

}