package ru.amk.company_list.repository

import io.reactivex.Single
import ru.amk.core.Company

interface CompanyRepository {

    fun getAllCompany(): Single<List<Company>>
}