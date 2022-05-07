package ru.amk.core.moex_model.company

import android.util.Log
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ru.amk.core.moex_model.company.json.History
import javax.inject.Inject

const val START_PAGE = 0
const val NEXT_PAGE = 100

class MoexCandleServiceNetworkImpl @Inject constructor(private val companiesService: MoexCandleService) :
    MoexCandleServiceNetwork {


//    @SuppressLint("CheckResult")
//    override fun getAllCompany(
//        pageNumber: Int,
//        date: String
//    ): Single<MoexCandleRaw> =
//        companiesService.getCompaniesByPage(pageNumber, date)
//            .flatMap {
//                //Загрузка и объединение всех страниц
//                val index = it.cursor.data[0][0]
//                val total = it.cursor.data[0][1]
//                if (index < total) {
//                    Single.just(it)
//                        .zipWith(getAllCompany(pageNumber + NEXT_PAGE, date)) { current, next ->
//                            mergePages(current, next)
//                        }
//                } else {
//                    Single.just(it)
//                }
//            }
//            .subscribeOn(Schedulers.io())

    override fun getMoexCandleServiceByCompany(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<MoexCompanyRaw> =
        companiesService.getMoexCandleByCompany(secId, dataFrom, dataTill)

    private fun mergePages(
        moexCompanyRaw: MoexCompanyRaw,
        moexCompanyRaw2: MoexCompanyRaw?,
    ): MoexCompanyRaw {

        val data = mutableListOf<List<Any>>()

        data.addAll(moexCompanyRaw.history.data)
        moexCompanyRaw2?.let { data.addAll(moexCompanyRaw2.history.data) }
        data.distinct()
        val resultData: List<List<Any>> = data
        val history = History(
            moexCompanyRaw.history.columns,
            data = resultData,
            moexCompanyRaw.history.metadata
        )
        return MoexCompanyRaw(history, moexCompanyRaw.cursor)
    }

    override fun getAllCompany(pageNumber: Int, date: String): Single<MoexCompanyRaw> =
        companiesService.getCompaniesByPage(pageNumber, date)
            //Загрузка и объединение всех страниц
            .flatMap { companyListRaw ->
                val index = companyListRaw.cursor.data[0][0]
                val total = companyListRaw.cursor.data[0][1]
                val pageSize = companyListRaw.cursor.data[0][2]

                if (index < total) {
                    Single.just(companyListRaw)
                        .zipWith(getAllCompany(pageNumber + pageSize, date)) { current, next ->
                            mergePages(current, next)
                        }
                } else {
                    Single.just(companyListRaw)
                }

            }

}




