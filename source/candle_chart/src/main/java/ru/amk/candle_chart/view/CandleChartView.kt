package ru.amk.candle_chart.view

import ru.amk.core.candle.Candle

interface CandleChartView {

    fun drawCandles(candles: List<Candle>)
    fun showNoData()
}