package br.com.neillon.home.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDetailsResponse(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val min: Double,
    @SerializedName("temp_max") val max: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int
)
