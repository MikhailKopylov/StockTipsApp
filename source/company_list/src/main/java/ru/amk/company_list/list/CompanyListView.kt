package ru.amk.company_list.list

import ru.amk.core.company.Company

interface CompanyListView {

    fun notifyAllDataChange(newListCompany: List<Company>)


}