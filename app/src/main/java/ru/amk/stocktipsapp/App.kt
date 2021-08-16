package ru.amk.stocktipsapp

import android.app.Application
import ru.amk.core.di.AppWithFacade
import ru.amk.core.mediator.ProvidesFacade

class App : Application(), AppWithFacade {

    companion object {
        lateinit var facadeComponent: FacadeComponent
    }

    override fun onCreate() {
        super.onCreate()
        facadeComponent = FacadeComponent.create(applicationContext)
    }

    override fun getFacade(): ProvidesFacade = facadeComponent
}