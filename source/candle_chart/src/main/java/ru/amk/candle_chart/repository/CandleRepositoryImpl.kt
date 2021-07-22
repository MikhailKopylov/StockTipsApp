package ru.amk.candle_chart.repository

import io.reactivex.Single
import ru.amk.core.candle.Candle
import ru.amk.core.candle.CandleRepositoryCore

class CandleRepositoryImpl(private val candleRepositoryCore: CandleRepositoryCore):CandleRepository {

    override fun getCandles(): Single<List<Candle>> =
        candleRepositoryCore.getCandleList()
}