package br.com.neillon.home.di

import br.com.neillon.home.data.api.OpenWeatherApi
import br.com.neillon.network.RetrofitFactory
import org.koin.dsl.module

object HomeModule {
    val dependencies = module {
        single { RetrofitFactory.createService<OpenWeatherApi>() }
    }
}