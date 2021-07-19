package ru.amk.company_list.item

import ru.amk.company_list.list.CompanyListPresenter

class ItemCompanyPresenterImpl(
    private val view: ItemView,
    private val companyListPresenter: CompanyListPresenter
) : ItemCompanyPresenter {

    override fun onBind(position: Int) {
        val company = companyListPresenter.getCompanyByPosition(position)
        view.setCompanyName(company.shortName)
        view.setCompanySetId(company.secId)
    }


}