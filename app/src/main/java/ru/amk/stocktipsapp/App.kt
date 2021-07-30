package ru.amk.stocktipsapp

import android.app.Application
import ru.amk.core.di.AppProvider
import ru.amk.core.di.AppWithFacade

class App:Application(), AppWithFacade {

    companion object{
        lateinit var appProvider: AppProvider
    }

    override fun onCreate() {
        super.onCreate()
        appProvider = AppComponent.createAppComponent(this)
    }

    override fun getAppProvider(): AppProvider  = appProvider
}