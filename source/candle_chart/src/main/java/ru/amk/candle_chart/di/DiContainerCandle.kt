package ru.amk.candle_chart.di

import android.app.Activity
import android.content.Context
import android.view.View
import ru.amk.candle_chart.R
import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.candle_chart.presenter.CandleChartPresenterImpl
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.repository.CandleRepositoryImpl
import ru.amk.candle_chart.view.CandleChartView
import ru.amk.candle_chart.view.CandleChartViewImpl
import ru.amk.core.candle.CandleRepositoryCoreNetwork
import ru.amk.core.di.DiContainerCore

class DiContainerCandle(context: Context) {

    private val candleRepository: CandleRepository = CandleRepositoryImpl(
        CandleRepositoryCoreNetwork(DiContainerCore().moexCandleServiceNetwork)
    )

    private val view:CandleChartView = ((context as Activity).findViewById<View>(R.id.candle_chart) as CandleChartViewImpl)

    val presenter:CandleChartPresenter = CandleChartPresenterImpl(candleRepository, view)
}