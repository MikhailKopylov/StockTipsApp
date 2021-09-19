package ru.amk.candle.view

import ru.amk.candle.CandleChartPresenter
import ru.amk.core.candle.Candle

interface CandlestickView {

    fun drawCandles(candles: List<Candle>)
    fun setCandlestickPresenter(candlestickPresenter: CandleChartPresenter)
    fun getWidthView(): Int
    fun isVisible():Boolean
    fun showNoData()
}