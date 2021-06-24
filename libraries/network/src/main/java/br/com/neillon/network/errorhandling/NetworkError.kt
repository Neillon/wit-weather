package br.com.neillon.network.errorhandling

data class NetworkError(
    val message: String,
    val status: Int,
    val exception: Throwable
)