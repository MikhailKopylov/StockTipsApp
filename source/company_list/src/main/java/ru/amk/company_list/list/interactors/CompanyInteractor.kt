package ru.amk.company_list.list.interactors

import io.reactivex.Flowable
import ru.amk.company_list.FavoriteCompany

interface CompanyInteractor {

    fun getCompanies():Flowable<List<FavoriteCompany>>
}