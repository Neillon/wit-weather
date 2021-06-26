package br.com.neillon.home.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)