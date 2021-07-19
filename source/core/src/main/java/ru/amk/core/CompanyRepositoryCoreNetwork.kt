package ru.amk.core

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.amk.core.moex_model.CreateMoexCandle

class CompanyRepositoryCoreNetwork(private val moexCandleService: MoexCandleService) :
    CompanyRepositoryCore {

    @SuppressLint("CheckResult")
    override fun getCompanyList(): Single<List<Company>> {
       val listCompany = mutableListOf<Company>()
        return   moexCandleService
            .getMoexCandleServiceByPage()

            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    listCompany.add(Company(item.SHORTNAME, item.SECID))
                }
                Single.just(listCompany)
            }
    }


}