package br.com.neillon.witweather

import android.app.Application
import br.com.neillon.navigator.di.NavigatorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WitWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WitWeatherApplication)
            modules(NavigatorModule.dependencies)
        }
    }
}