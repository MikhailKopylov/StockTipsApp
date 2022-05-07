package ru.amk.core.moex_model.company

import io.reactivex.Single

interface MoexCandleServiceNetwork {

    fun getAllCompany(pageNumber:Int, date:String): Single<MoexCompanyRaw>
    fun getMoexCandleServiceByCompany(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<MoexCompanyRaw>

}