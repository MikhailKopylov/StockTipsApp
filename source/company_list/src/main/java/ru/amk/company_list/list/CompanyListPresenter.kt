package ru.amk.company_list.list

import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.list.handlers.SortedBy
import ru.amk.core.company.Company

interface CompanyListPresenter {

    fun onViewCreated()
    fun getCompanyByPosition(position: Int): FavoriteCompany
    fun getCount(): Int
    fun clickSorting(sorted: SortedBy)
    fun clickFavoriteUp(isFavoriteUp:Boolean)
    fun addFavoriteCompany(company: Company)
    fun deleteCompanyFromFavorite(company: Company)
    fun viewOnStop()
    fun onCleared()
    fun filterCompany(filterName: String)
}