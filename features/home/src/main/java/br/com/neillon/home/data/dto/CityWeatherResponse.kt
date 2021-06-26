package br.com.neillon.home.data.dto

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    @SerializedName("coord") val coordinates: CityCoordinatesResponse,
    @SerializedName("weather") val weather: List<WeatherResponse>?,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: WeatherDetailsResponse,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind") val wind: WindResponse,
    @SerializedName("dt") val dt: Long,
    @SerializedName("name") val name: String
)