package br.com.neillon.home.di

import br.com.neillon.home.data.api.OpenWeatherApi
import br.com.neillon.home.data.mapper.WeatherResponseMapper
import br.com.neillon.home.data.mapper.WeatherResponseMapperImpl
import br.com.neillon.home.data.repositories.WeatherRepositoryImpl
import br.com.neillon.home.domain.abstractions.WeatherRepository
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameUseCase
import br.com.neillon.home.domain.usecases.GetWeatherByCityNameUseCaseImpl
import br.com.neillon.home.domain.usecases.GetWeatherByLatLongUseCase
import br.com.neillon.home.domain.usecases.GetWeatherByLatLongUseCaseImpl
import br.com.neillon.home.presentation.mapper.CityWeatherUiMapper
import br.com.neillon.home.presentation.mapper.CityWeatherUiMapperImpl
import br.com.neillon.home.presentation.ui.HomeViewModel
import br.com.neillon.network.RetrofitFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object HomeModule {
    val dependencies = module {
        // Api
        single { RetrofitFactory.createService<OpenWeatherApi>() }

        // Repository
        factory { WeatherRepositoryImpl(get(), get()) } bind WeatherRepository::class

        // UseCase
        factory { GetWeatherByCityNameUseCaseImpl(get()) } bind GetWeatherByCityNameUseCase::class
        factory { GetWeatherByLatLongUseCaseImpl(get()) } bind GetWeatherByLatLongUseCase::class

        // Mappers
        factory { WeatherResponseMapperImpl() } bind WeatherResponseMapper::class
        factory { CityWeatherUiMapperImpl() } bind CityWeatherUiMapper::class

        // ViewModels
        viewModel { HomeViewModel(get(), get(), get()) }
    }
}