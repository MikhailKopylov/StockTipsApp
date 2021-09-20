package ru.amk.candle_chart.di

import dagger.Binds
import dagger.Module
import ru.amk.candle.CandleChartPresenter
import ru.amk.candle_chart.presenter.CandleChartPresenterImpl
import ru.amk.candle_chart.presenter.ChartPresenter
import ru.amk.candle_chart.presenter.ChartPresenterImpl
import ru.amk.candle_chart.presenter.ThreeLineBreakPresenterImpl
import ru.amk.candle_chart.repository.ChartRepository
import ru.amk.candle_chart.repository.ChartRepositoryImpl
import ru.amk.core.di.ActivityScope
import ru.amk.three_line_break.ThreeLineBreakPresenter

@Module
abstract class ChartModule {

    @Binds
    @ActivityScope
    abstract fun bindCandleRepository(candleRepository: ChartRepositoryImpl):ChartRepository

    @Binds
    @ActivityScope
    abstract fun bindCandlestickPresenter(CandleChartPresenter:CandleChartPresenterImpl): CandleChartPresenter

    @Binds
    @ActivityScope
    abstract fun bindThreeLineBreakPresenter(threeLineBreakPresenter:ThreeLineBreakPresenterImpl): ThreeLineBreakPresenter

    @Binds
    @ActivityScope
    abstract fun bindCartPresenter(chartPresenter: ChartPresenterImpl):ChartPresenter
}