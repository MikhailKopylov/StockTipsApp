package ru.amk.candle_chart.di

import android.app.Activity
import android.content.Context
import android.view.View
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.candle_chart.R
import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.candle_chart.presenter.CandleChartPresenterImpl
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.repository.CandleRepositoryImpl
import ru.amk.candle_chart.view.CandleChartView
import ru.amk.candle_chart.view.CandleChartViewImpl

@Module
abstract class CandleModule {

    companion object{

        @Provides
        fun provideCandleChartView(context: Context): CandleChartView = ((context as Activity).findViewById<View>(
            R.id.candle_chart) as CandleChartViewImpl)
    }

    @Binds
    abstract fun bindCandleRepository(candleRepository: CandleRepositoryImpl):CandleRepository

    @Binds
    abstract fun bindPresenter(CandleChartPresenter:CandleChartPresenterImpl):CandleChartPresenter
}