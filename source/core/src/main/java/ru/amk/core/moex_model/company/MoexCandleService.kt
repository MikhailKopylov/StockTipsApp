package ru.amk.core.moex_model.company

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoexCandleService {

    @GET("history/engines/stock/markets/shares/boards/TQBR/securities.json?date=2021-07-07&start=0")
    fun getMoexCandleAllCompanyByPage(@Query("start") start:Int):Single<MoexCandleRaw>

    @GET("history/engines/stock/markets/shares/boards/TQBR/securities/SBER/candles.json?from=2021-05-01&till=2021-07-21")
    fun getMoexCandleByCompany():Single<MoexCandleRaw>

}