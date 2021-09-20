package ru.amk.candle_chart.di

import android.widget.HorizontalScrollView
import dagger.BindsInstance
import dagger.Component
import ru.amk.candle_chart.CandleChartActivity
import ru.amk.base_view_chart.AxisYView
import ru.amk.candle.view.CandlestickView
import ru.amk.core.di.ActivityScope
import ru.amk.core.di.AppProvider
import ru.amk.core.di.CoreComponent
import ru.amk.three_line_break.view.ThreeLineBreakView

@Component(
    dependencies = [CoreComponent::class, AppProvider::class],
    modules = [ChartModule::class]
)
@ActivityScope
interface ChartComponent {


    fun inject(activity: CandleChartActivity)

    @Component.Builder
    interface CandleChartComponentBuilder {

        fun build(): ChartComponent

        @BindsInstance
        fun candleChartView(candlestickView: CandlestickView): CandleChartComponentBuilder

        @BindsInstance
        fun threeLineBreakChartView(threeLineBreakView: ThreeLineBreakView): CandleChartComponentBuilder

        @BindsInstance
        fun axisYView(axisYView: AxisYView): CandleChartComponentBuilder

        @BindsInstance
        fun scrollView(scrollView: HorizontalScrollView): CandleChartComponentBuilder

        fun coreComponent(coreComponent: CoreComponent): CandleChartComponentBuilder

        fun appProvider(appProvider: AppProvider): CandleChartComponentBuilder
    }
}