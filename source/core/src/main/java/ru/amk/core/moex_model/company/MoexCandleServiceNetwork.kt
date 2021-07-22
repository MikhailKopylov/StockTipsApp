package ru.amk.core.moex_model.company

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ru.amk.core.moex_model.company.json.History

class MoexCandleServiceNetwork(private val moexCandleService: MoexCandleService) {

    @SuppressLint("CheckResult")
    fun getMoexCandleServiceAllCompanyByPage(): Single<MoexCandleRaw> =
        moexCandleService.getMoexCandleAllCompanyByPage(0)
            .zipWith(moexCandleService.getMoexCandleAllCompanyByPage(100),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                }).zipWith(moexCandleService.getMoexCandleAllCompanyByPage(200),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                })
            .subscribeOn(Schedulers.io())

    fun getMoexCandleServiceByCompany():Single<MoexCandleRaw> =
        moexCandleService.getMoexCandleByCompany()

    private fun mergeMoexCandlePage(
        moexCandleRaw: MoexCandleRaw,
        moexCandleRaw2: MoexCandleRaw?,
    ): MoexCandleRaw {

        val data = mutableListOf<List<Any>>()

        data.addAll(moexCandleRaw.history.data)
        moexCandleRaw2?.let { data.addAll(moexCandleRaw2.history.data) }
        data.distinct()
        val resultData: List<List<Any>> = data
        val history = History(
            moexCandleRaw.history.columns,
            data = resultData,
            moexCandleRaw.history.metadata
        )
        return MoexCandleRaw(history, moexCandleRaw.cursor)
    }
}




