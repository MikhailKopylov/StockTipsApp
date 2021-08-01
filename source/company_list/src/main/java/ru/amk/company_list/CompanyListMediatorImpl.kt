package ru.amk.company_list

import android.content.Context
import ru.amk.core.mediator.CompanyListMediator
import javax.inject.Inject

class CompanyListMediatorImpl @Inject constructor() : CompanyListMediator {
    override fun openCompanyList(context: Context) {
        CompanyListActivity.startCompanyListActivity(context)
    }
}