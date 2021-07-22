package ru.amk.core.company

import android.annotation.SuppressLint
import io.reactivex.Single
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.CreateMoexCandle

class CompanyRepositoryCoreNetwork(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
    CompanyRepositoryCore {

    @SuppressLint("CheckResult")
    override fun getCompanyList(): Single<List<Company>> {
       val listCompany = mutableListOf<Company>()
        return   moexCandleServiceNetwork
            .getMoexCandleServiceAllCompanyByPage()

            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    listCompany.add(Company(item.SHORTNAME, item.SECID))
                }
                Single.just(listCompany)
            }
    }


}