package ru.amk.company_list.list

import android.content.Context
import ru.amk.core.company.Company

interface CompanyListPresenter {

    fun onViewCreated()
    fun getCompanyByPosition(position: Int): Company
    fun getCount(): Int
    fun openCandleScreen(context: Context, secId: String, dateTill: String)
    fun onCleared()
}