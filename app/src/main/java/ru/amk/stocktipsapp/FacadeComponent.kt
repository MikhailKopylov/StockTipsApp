package ru.amk.stocktipsapp

import android.content.Context
import dagger.Component
import ru.amk.core.mediator.AppProvider
import ru.amk.core.mediator.ProvidesFacade

@Component(
    dependencies = [AppProvider::class],
    modules = [MediatorsModule::class]
)

interface FacadeComponent : ProvidesFacade {

    companion object {

        fun create(context: Context): FacadeComponent {
            return DaggerFacadeComponent.builder()
                .appProvider(AppComponent.createAppComponent(context)).build()
        }
    }

    fun inject(app: App)
}