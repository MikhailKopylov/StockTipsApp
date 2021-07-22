package ru.amk.candle_chart.repository

import io.reactivex.Single
import ru.amk.core.candle.Candle

interface CandleRepository {

    fun getCandles(): Single<List<Candle>>
}