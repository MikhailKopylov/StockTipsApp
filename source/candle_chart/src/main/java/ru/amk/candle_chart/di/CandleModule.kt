package ru.amk.candle_chart.di

import dagger.Binds
import dagger.Module
import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.candle_chart.presenter.CandleChartPresenterImpl
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.repository.CandleRepositoryImpl
import ru.amk.core.di.ActivityScope

@Module
abstract class CandleModule {

    @Binds
    @ActivityScope
    abstract fun bindCandleRepository(candleRepository: CandleRepositoryImpl):CandleRepository

    @Binds
    @ActivityScope
    abstract fun bindPresenter(CandleChartPresenter:CandleChartPresenterImpl):CandleChartPresenter
}