package br.com.neillon.navigator.di

import br.com.neillon.core.navigation.Navigator
import br.com.neillon.navigator.NavigatorImpl
import org.koin.dsl.module

object NavigatorModule {
    val dependencies = module {
        single<Navigator> { NavigatorImpl() }
    }
}