package ru.amk.stocktipsapp

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.amk.core.di.AppProvider
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent : AppProvider {
    companion object {

        private var appProvider: AppProvider? = null

        fun createAppComponent(context: Context): AppProvider =
            appProvider ?: DaggerAppComponent.builder().context(context).build()
    }

    @Component.Builder
    interface AppComponentBuilder {

        @BindsInstance
        fun context(context: Context): AppComponentBuilder

        fun build(): AppComponent

    }
}