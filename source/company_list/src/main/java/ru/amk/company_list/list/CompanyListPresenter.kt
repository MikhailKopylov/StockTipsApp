package ru.amk.company_list.list

import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.OrderBy
import ru.amk.company_list.SortedBy
import ru.amk.core.company.Company

interface CompanyListPresenter {

    fun onViewCreated()
    fun getCompanyByPosition(position: Int): FavoriteCompany
    fun getCount(): Int
    fun sortBy(sortedBy: SortedBy, orderBy: OrderBy, favoriteUp: Boolean)
    fun addFavoriteCompany(company: Company)
    fun deleteCompanyFromFavorite(company: Company)
    fun onCleared()
    fun filterCompany(filterName: String)
    fun onViewResume()
}