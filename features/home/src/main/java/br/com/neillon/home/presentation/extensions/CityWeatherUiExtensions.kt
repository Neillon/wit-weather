package br.com.neillon.home.presentation.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Int.toCelsius() = "$this°C"

fun Int.toAtmosphericPressure() = "${this}hPa"

fun Double.toWindVelocity() = "${BigDecimal(this).setScale(2, RoundingMode.HALF_UP)}m/s E"

fun Int.toPercentageString() = "$this%"