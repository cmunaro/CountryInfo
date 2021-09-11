package com.cmunaro.countryinfo

import android.app.Application
import com.cmunaro.countryinfo.di.networkModule
import com.cmunaro.countryinfo.di.viewModelModule
import org.koin.core.context.startKoin

class CountryInfoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModule, viewModelModule)
        }
    }
}