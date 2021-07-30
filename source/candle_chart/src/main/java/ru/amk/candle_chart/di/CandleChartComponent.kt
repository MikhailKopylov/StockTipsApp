package ru.amk.candle_chart.di

import dagger.Component
import ru.amk.candle_chart.CandleChartActivity
import ru.amk.core.di.AppProvider
import ru.amk.core.di.CoreComponent

@Component(dependencies = [CoreComponent::class, AppProvider::class],
modules = [CandleModule::class])
interface CandleChartComponent {



    fun inject(activity:CandleChartActivity)
}