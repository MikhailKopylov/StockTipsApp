package ru.amk.core.company

import io.reactivex.Single

interface CompanyRepositoryCore {

    fun getCompanyList(): Single<List<Company>>
}