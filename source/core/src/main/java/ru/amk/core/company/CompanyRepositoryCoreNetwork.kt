package ru.amk.core.company

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Single
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.CreateMoexCandle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CompanyRepositoryCoreNetwork(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
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
    override fun getCompanyList(date:String): Single<List<Company>> {

        val listCompany = mutableListOf<Company>()
        return moexCandleServiceNetwork
            .getMoexCandleServiceAllCompanyByPage(date)
            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    listCompany.add(Company(item.SHORTNAME, item.SECID, item.TRADEDATE))
                }
                //Если выходной, то поиск последнего рабочего дня
                if (listCompany.isEmpty()) {
                    _prevDate = LocalDate.parse(date).minusDays(1L).toString()
                    getCompanyList(_prevDate)
                }else {
                    Single.just(listCompany)
                }
            }
    }

    override fun getCurrentDate(): String = _date


}