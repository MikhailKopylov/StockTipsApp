package ru.amk.company_list.repository

import io.reactivex.Single
import ru.amk.core.company.Company
import ru.amk.core.company.CompanyRepositoryCore

class CompanyRepositoryImpl(
    private val companyRepositoryCore: CompanyRepositoryCore
) : CompanyRepository {

    override fun getAllCompany(): Single<List<Company>> =
        companyRepositoryCore.getCompanyList(companyRepositoryCore.getCurrentDate())
}