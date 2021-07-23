package ru.amk.company_list

import android.content.Context
import ru.amk.core.mediator.CompanyListMediator

class CompanyListMediatorImpl : CompanyListMediator {
    override fun openCompanyList(context: Context) {
        CompanyListActivity.startCompanyListActivity(context)
    }
}