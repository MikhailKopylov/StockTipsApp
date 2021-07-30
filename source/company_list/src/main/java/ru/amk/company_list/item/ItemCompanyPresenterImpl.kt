package ru.amk.company_list.item

import ru.amk.company_list.list.CompanyListPresenter
import javax.inject.Inject


class ItemCompanyPresenterImpl @Inject constructor(
    private val view: ItemView,
    private val companyListPresenter: CompanyListPresenter
) : ItemCompanyPresenter {

    override fun onBind(position: Int) {
        val company = companyListPresenter.getCompanyByPosition(position)
        view.setCompanyName(company.shortName)
        view.setCompanySetId(company.secId)
    }

    override fun onClickItem(position: Int) {
        view.openCandleScreen(
            companyListPresenter.getCompanyByPosition(position).secId,
            companyListPresenter.getCompanyByPosition(position).date
        )
    }


}