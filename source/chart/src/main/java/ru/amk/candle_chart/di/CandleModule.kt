package ru.amk.candle_chart.di

import dagger.Binds
import dagger.Module
import ru.amk.candle.CandleChartPresenter
import ru.amk.candle_chart.presenter.CandleChartPresenterImpl
import ru.amk.candle_chart.presenter.ThreeLineBreakPresenterImpl
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.repository.CandleRepositoryImpl
import ru.amk.core.di.ActivityScope
import ru.amk.three_line_break.ThreeLineBreakPresenter

@Module
abstract class CandleModule {

    @Binds
    @ActivityScope
    abstract fun bindCandleRepository(candleRepository: CandleRepositoryImpl):CandleRepository

    @Binds
    @ActivityScope
    abstract fun bindCandlestickPresenter(CandleChartPresenter:CandleChartPresenterImpl): CandleChartPresenter

    @Binds
    @ActivityScope
    abstract fun bindThreeLineBreakPresenter(threeLineBreakPresenter:ThreeLineBreakPresenterImpl): ThreeLineBreakPresenter
}