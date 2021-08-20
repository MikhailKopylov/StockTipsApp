package ru.amk.candle_chart.view

import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.core.candle.Candle

interface CandleChartView {

    fun drawCandles(candles: List<Candle>)
    fun setExpensePresenter(candleChartPresenter: CandleChartPresenter)
    fun getWidthView(): Int
    fun showNoData()
}