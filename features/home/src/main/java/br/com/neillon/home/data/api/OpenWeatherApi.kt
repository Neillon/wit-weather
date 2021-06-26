package br.com.neillon.home.data.api

import br.com.neillon.home.data.dto.CityWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    companion object {
        const val BASE_URL = "/data/2.5"
    }

    @GET("$BASE_URL/weather")
    suspend fun getWeatherFromCity(
        @Query("q") query: String
    ): Response<CityWeatherResponse>

    @GET("$BASE_URL/weather")
    suspend fun getWeatherFromLatLong(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<CityWeatherResponse>

}