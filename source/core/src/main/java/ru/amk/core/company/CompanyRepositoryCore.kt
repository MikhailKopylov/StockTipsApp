package ru.amk.core.company

import io.reactivex.Single

interface CompanyRepositoryCore {

    fun getCompanyList(date:String): Single<List<Company>>
    fun getCurrentDate():String
}