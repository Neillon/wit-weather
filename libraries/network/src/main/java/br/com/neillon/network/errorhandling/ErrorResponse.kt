package br.com.neillon.network.errorhandling

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: String
)