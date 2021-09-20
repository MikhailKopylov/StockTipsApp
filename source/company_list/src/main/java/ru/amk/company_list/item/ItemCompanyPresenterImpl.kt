package ru.amk.company_list.item

import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.list.CompanyListPresenter
import javax.inject.Inject


class ItemCompanyPresenterImpl @Inject constructor(
    private val view: ItemView,
    private val companyListPresenter: CompanyListPresenter
) : ItemCompanyPresenter {

    override fun onBind(position: Int) {
        val company = companyListPresenter.getCompanyByPosition(position)
        view.setCompanyName(company.company.shortName)
        view.setCompanySetId(company.company.secId)
        view.setFavorite(company.isFavorite)
    }

    override fun onClickItem(adapterPosition: Int) {
        view.openCandleScreen(
            companyListPresenter.getCompanyByPosition(adapterPosition).company,
            companyListPresenter.getCompanyByPosition(adapterPosition).isFavorite
        )
    }

    override fun onClickFavorite(adapterPosition: Int) {
        val favoriteCompany: FavoriteCompany =
            companyListPresenter.getCompanyByPosition(adapterPosition)

        if (favoriteCompany.isFavorite) {
            companyListPresenter.deleteCompanyFromFavorite(favoriteCompany.company)
        } else {
            companyListPresenter.addFavoriteCompany(favoriteCompany.company)
        }
    }


}