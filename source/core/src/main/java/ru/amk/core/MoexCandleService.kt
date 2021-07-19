package ru.amk.core

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ru.amk.core.moex_model.MoexCandleRaw
import ru.amk.core.moex_model.json.History

class MoexCandleService(private val moexCandleServiceByPage: MoexCandleServiceByPage) {

    @SuppressLint("CheckResult")
    fun getMoexCandleServiceByPage(): Single<MoexCandleRaw> =
        moexCandleServiceByPage.getMoexCandleByPage(0)
            .zipWith(moexCandleServiceByPage.getMoexCandleByPage(100),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                }).zipWith(moexCandleServiceByPage.getMoexCandleByPage(200),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                })
            .subscribeOn(Schedulers.io())


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




