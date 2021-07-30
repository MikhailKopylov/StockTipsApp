package ru.amk.core.moex_model.company

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ru.amk.core.moex_model.company.json.History
import javax.inject.Inject

const val START_PAGE = 0
const val NEXT_PAGE = 100

class MoexCandleServiceNetworkImpl @Inject constructor (private val moexCandleService: MoexCandleService) :
    MoexCandleServiceNetwork {


    @SuppressLint("CheckResult")
    override fun getMoexCandleServiceAllCompany(
        pageNumber: Int,
        date: String
    ): Single<MoexCandleRaw> =
        moexCandleService.getMoexCandleAllCompanyByPage(pageNumber, date)
            .flatMap {
                //Загрузка и объединение всех страниц
                val index = it.cursor.data[0][0]
                val total = it.cursor.data[0][1]
                if (index < total) {
                    Single.just(it)
                        .zipWith(
                            getMoexCandleServiceAllCompany(pageNumber + NEXT_PAGE, date)
                        ) { a, b ->
                            mergeMoexCandlePage(a, b)
                        }
                } else {
                    Single.just(it)
                }
            }
            .subscribeOn(Schedulers.io())

    override fun getMoexCandleServiceByCompany(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<MoexCandleRaw> =
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




