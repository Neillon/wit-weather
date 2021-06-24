package br.com.neillon.home.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/weather")
    fun getWeatherFromCity(
        @Query("q") query: String,
    )

    @GET("/weather")
    fun getWeatherFromLatLong(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    )

}