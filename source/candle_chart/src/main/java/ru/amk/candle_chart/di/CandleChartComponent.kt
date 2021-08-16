package ru.amk.candle_chart.di

import dagger.BindsInstance
import dagger.Component
import ru.amk.candle_chart.CandleChartActivity
import ru.amk.candle_chart.view.CandleChartView
import ru.amk.core.di.ActivityScope
import ru.amk.core.mediator.AppProvider
import ru.amk.core.di.CoreComponent

@Component(
    dependencies = [CoreComponent::class, AppProvider::class],
    modules = [CandleModule::class]
)
@ActivityScope
interface CandleChartComponent {


    fun inject(activity: CandleChartActivity)

    @Component.Builder
    interface CandleChartComponentBuilder {

        fun build(): CandleChartComponent

        @BindsInstance
        fun candleChartView(candleChartView: CandleChartView): CandleChartComponentBuilder

        fun coreComponent(coreComponent: CoreComponent): CandleChartComponentBuilder

        fun appProvider(appProvider: AppProvider): CandleChartComponentBuilder
    }
}