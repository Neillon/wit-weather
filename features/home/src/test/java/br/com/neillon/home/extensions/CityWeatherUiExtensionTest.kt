package br.com.neillon.home.extensions

import br.com.neillon.home.presentation.extensions.toAtmosphericPressure
import br.com.neillon.home.presentation.extensions.toCelsius
import br.com.neillon.home.presentation.extensions.toPercentageString
import br.com.neillon.home.presentation.extensions.toWindVelocity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal
import java.math.RoundingMode

@RunWith(JUnit4::class)
class CityWeatherUiExtensionTest {

    @Test
    fun `should convert value to Celsius`() {
        val expected = "20°C"
        assertEquals(expected, 20.toCelsius())
    }

    @Test
    fun `should convert value to AtmosphericPressure`() {
        val expected = "50hPa"
        assertEquals(expected, 50.toAtmosphericPressure())
    }

    @Test
    fun `should convert value to WindVelocity`() {
        val expected = "51.50m/s E"
        assertEquals(expected, 51.49999.toWindVelocity())
    }

    @Test
    fun `should convert value to PercentageString`() {
        val expected = "10%"
        assertEquals(expected, 10.toPercentageString())
    }
}