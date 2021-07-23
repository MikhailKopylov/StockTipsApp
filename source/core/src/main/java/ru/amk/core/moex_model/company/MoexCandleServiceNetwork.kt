package ru.amk.core.moex_model.company

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ru.amk.core.moex_model.company.json.History
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MoexCandleServiceNetwork(private val moexCandleService: MoexCandleService) {


    //TODO Не придумал, как динамически, по количеству страниц, сделать запросы и объединить их результаты
    @SuppressLint("CheckResult")
    fun getMoexCandleServiceAllCompanyByPage(date:String): Single<MoexCandleRaw> =
        moexCandleService.getMoexCandleAllCompanyByPage(0, date)
            .zipWith(moexCandleService.getMoexCandleAllCompanyByPage(100, date),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                }).zipWith(moexCandleService.getMoexCandleAllCompanyByPage(200, date),
                { a, b ->
                    mergeMoexCandlePage(a, b)
                })
            .subscribeOn(Schedulers.io())

    fun getMoexCandleServiceByCompany(secId:String, dataFrom:String, dataTill:String):Single<MoexCandleRaw> =
        moexCandleService.getMoexCandleByCompany(secId, dataFrom, dataTill)

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




