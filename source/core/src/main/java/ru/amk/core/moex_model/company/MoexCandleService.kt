package ru.amk.core.moex_model.company

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoexCandleService {

    @GET("history/engines/stock/markets/shares/boards/TQBR/securities.json?iss.meta=on")
    fun getMoexCandleAllCompanyByPage(@Query("start") start: Int, @Query("date") date:String): Single<MoexCandleRaw>

    @GET("history/engines/stock/markets/shares/boards/TQBR/securities/{secId}/candles.json?start=0")
    fun getMoexCandleByCompany(
        @Path("secId") secId: String,
        @Query("from") dataFrom: String,
        @Query("till") dataTill: String
    ): Single<MoexCandleRaw>

}