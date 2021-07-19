package ru.amk.company_list.list

import ru.amk.core.Company

interface CompanyListView {

    fun notifyAllDataChange(newListCompany: List<Company>)


}