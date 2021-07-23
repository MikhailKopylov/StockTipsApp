package ru.amk.core.candle

import io.reactivex.Single

interface CandleRepositoryCore {

    fun getCandleList(secId:String, dataFrom:String, dataTill:String): Single<List<Candle>>
}