package ru.amk.company_list.list

import ru.amk.company_list.FavoriteCompany
import ru.amk.core.company.Company

interface CompanyListPresenter {

    fun onViewCreated()
    fun getCompanyByPosition(position:Int): FavoriteCompany
    fun getCount():Int
    fun sortBy(sortedBy:SortedBy)
    fun addFavoriteCompany(company: Company)
    fun deleteCompanyFromFavorite(company: Company)
    fun setFavoriteCompanyUp(isToUp:Boolean)
    fun onCleared()
}