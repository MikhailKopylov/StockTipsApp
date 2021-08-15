package ru.amk.company_list.repository

import io.reactivex.Single
import ru.amk.core.company.Company
import ru.amk.core.company.CompanyRepositoryCore
import javax.inject.Inject

class CompanyListRepositoryImpl @Inject constructor(
    private val companyRepositoryCore: CompanyRepositoryCore
) : CompanyListRepository {

    override fun getAllCompany(): Single<List<Company>> =
        companyRepositoryCore.getCompanyList(companyRepositoryCore.getCurrentDate())
}