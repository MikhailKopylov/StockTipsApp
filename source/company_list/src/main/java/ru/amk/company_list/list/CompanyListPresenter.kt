package ru.amk.company_list.list

import ru.amk.core.Company

interface CompanyListPresenter {

    fun onViewCreated()
    fun getCompanyByPosition(position:Int):Company
    fun getCount():Int
}