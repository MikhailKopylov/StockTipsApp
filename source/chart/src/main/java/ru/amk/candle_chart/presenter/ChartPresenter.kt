package ru.amk.candle_chart.presenter

import ru.amk.candle_chart.repository.ChartRepository
import ru.amk.core.company.Company
import javax.inject.Inject

interface ChartPresenter {

    fun changeFavoriteStatus(isFavorite: Boolean,  company: Company)
}

class ChartPresenterImpl @Inject constructor(
    private val chartRepository: ChartRepository
) : ChartPresenter {
    override fun changeFavoriteStatus(isFavorite: Boolean, company: Company) {
        if (isFavorite) {
            chartRepository.addCompanyToFavorite(company)
        } else{
            chartRepository.deleteCompanyFromFavorite(company)
        }
    }


}