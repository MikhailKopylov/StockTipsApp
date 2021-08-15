package ru.amk.candle_chart.repository

import io.reactivex.Single
import ru.amk.core.candle.Candle
import ru.amk.core.candle.CandleRepositoryCore
import javax.inject.Inject

class CandleRepositoryImpl @Inject constructor(private val candleRepositoryCore: CandleRepositoryCore):CandleRepository {

    override fun getCandles(secId:String, dataFrom:String, dataTill:String): Single<List<Candle>> =
        candleRepositoryCore.getCandleList(secId, dataFrom, dataTill)
}