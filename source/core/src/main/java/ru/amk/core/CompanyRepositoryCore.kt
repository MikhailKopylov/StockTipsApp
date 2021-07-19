package ru.amk.core

import io.reactivex.Single

interface CompanyRepositoryCore {

    fun getCompanyList(): Single<List<Company>>
}