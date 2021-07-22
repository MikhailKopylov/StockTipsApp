package ru.amk.core.candle

import io.reactivex.Single

interface CandleRepositoryCore {

    fun getCandleList(): Single<List<Candle>>
}