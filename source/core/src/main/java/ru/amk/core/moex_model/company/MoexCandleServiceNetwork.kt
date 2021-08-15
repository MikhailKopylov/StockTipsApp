package ru.amk.core.moex_model.company

import io.reactivex.Single

interface MoexCandleServiceNetwork {

    fun getMoexCandleServiceAllCompany(pageNumber:Int, date:String): Single<MoexCandleRaw>
    fun getMoexCandleServiceByCompany(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<MoexCandleRaw>

}