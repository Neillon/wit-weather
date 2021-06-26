package br.com.neillon.home.data.api

import br.com.neillon.home.data.dto.CityWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/weather")
    suspend fun getWeatherFromCity(
        @Query("q") query: String,
    ): Response<CityWeatherResponse>

    @GET("/weather")
    suspend fun getWeatherFromLatLong(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Response<CityWeatherResponse>

}