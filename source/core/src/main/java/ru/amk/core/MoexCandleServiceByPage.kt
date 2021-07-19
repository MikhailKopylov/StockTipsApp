package ru.amk.core

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.amk.core.moex_model.MoexCandleRaw

interface MoexCandleServiceByPage {

    @GET("history/engines/stock/markets/shares/boards/TQBR/securities.json?date=2021-07-07&start=0")
    fun getMoexCandleByPage(@Query("start") start:Int):Single<MoexCandleRaw>


}