package ru.amk.core.company

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Single
import ru.amk.core.moex_model.company.CreateMoexCandle
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.START_PAGE
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class CompanyRepositoryCoreNetwork @Inject constructor(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
    CompanyRepositoryCore {

    private val _date: String
        @SuppressLint("SimpleDateFormat") @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = LocalDate.parse(sdf.format(Date()))
            return currentDate.minusDays(1L).toString()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private var _prevDate: String = LocalDate.parse(_date).minusDays(1L).toString()


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun getCompanyList(date: String): Single<List<Company>> =
        getCompanyWithoutChangePrice(date).flatMap { companyPresentDay ->
            val prevDay =
                LocalDate.parse(companyPresentDay.map {
                    it.value.date
                }.first { true }).minusDays(1L)
                    .toString()
            getCompanyWithoutChangePrice(prevDay).flatMap {
                Single.just(calcChangePrice(companyPresentDay, it))
            }
        }


    private fun calcChangePrice(
        companyPresentDay: Map<String, CompanyWithoutChangePrice>,
        companyPrevDay: Map<String, CompanyWithoutChangePrice>
    ): List<Company> {
        val companyList = mutableListOf<Company>()
        companyPresentDay.map { companyMap ->
            val changePrice =
                companyPrevDay[companyMap.key]?.let { companyMap.value.lastPrice - it.lastPrice }
                    ?: 0.0
            val changePricePercent =
                companyPrevDay[companyMap.key]?.let { (companyMap.value.lastPrice / it.lastPrice * 100) - 100 }
                    ?: 0.0
            with(companyMap.value) {
                companyList.add(
                    Company(
                        shortName,
                        secId,
                        date,
                        lastPrice,
                        changePrice,
                        changePricePercent
                    )
                )
            }
        }
        return companyList
    }

    override fun getCurrentDate(): String = _date

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCompanyWithoutChangePrice(date: String): Single<Map<String, CompanyWithoutChangePrice>> {
        val listCompany = mutableMapOf<String, CompanyWithoutChangePrice>()
        return moexCandleServiceNetwork
            .getMoexCandleServiceAllCompany(START_PAGE, date)
            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    listCompany[item.SECID] = CompanyWithoutChangePrice(
                        item.SHORTNAME,
                        item.SECID,
                        item.TRADEDATE,
                        item.LEGALCLOSEPRICE
                    )
                }
                //Если выходной, то поиск последнего рабочего дня
                if (listCompany.isEmpty()) {
                    _prevDate = LocalDate.parse(date).minusDays(1L).toString()
                    getCompanyWithoutChangePrice(_prevDate)
                } else {
                    Single.just(listCompany)
                }
            }
    }
}