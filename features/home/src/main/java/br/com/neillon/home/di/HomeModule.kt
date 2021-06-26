package br.com.neillon.home.di

import br.com.neillon.home.data.api.OpenWeatherApi
import br.com.neillon.home.data.mapper.WeatherResponseMapper
import br.com.neillon.home.data.mapper.WeatherResponseMapperImpl
import br.com.neillon.home.data.repositories.WeatherRepositoryImpl
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.usecases.GetWeatherByCityName
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameImpl
import br.com.neillon.home.domain.usecases.GetWeatherByLatLong
import br.com.neillon.home.domain.usecases.GetWeatherByLatLongImpl
import br.com.neillon.network.RetrofitFactory
import org.koin.dsl.bind
import org.koin.dsl.module

object HomeModule {
    val dependencies = module {
        // Api
        single { RetrofitFactory.createService<OpenWeatherApi>() }

        // Repository
        factory { WeatherRepositoryImpl(get(), get()) } bind WeatherRepository::class

        // UseCase
        factory { GetWeatherByCityNameImpl(get()) } bind GetWeatherByCityName::class
        factory { GetWeatherByLatLongImpl(get()) } bind GetWeatherByLatLong::class

        // Mappers
        factory { WeatherResponseMapperImpl() } bind WeatherResponseMapper::class
    }
}