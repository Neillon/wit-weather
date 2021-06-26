package br.com.neillon.home.data.dto

import com.google.gson.annotations.SerializedName

data class CityCoordinatesResponse(
    @SerializedName("lon") val longitude: Double,
    @SerializedName("lat") val latitude: Double
)