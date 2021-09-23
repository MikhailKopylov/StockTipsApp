package ru.amk.candle_chart.presenter

import ru.amk.candle_chart.ChartView
import ru.amk.candle_chart.repository.ChartRepository
import ru.amk.core.candle.ColorCandle
import ru.amk.core.company.Company
import ru.amk.core.percentFormat
import ru.amk.core.priceFormat
import javax.inject.Inject

interface ChartPresenter {

    fun changeFavoriteStatus(isFavorite: Boolean, company: Company)
    fun setLastPrice(company: Company)
    fun setChangePrice(company: Company)
}

class ChartPresenterImpl @Inject constructor(
    private val chartRepository: ChartRepository,
    private val chartView: ChartView
) : ChartPresenter {
    override fun changeFavoriteStatus(isFavorite: Boolean, company: Company) {
        if (isFavorite) {
            chartRepository.addCompanyToFavorite(company)
        } else {
            chartRepository.deleteCompanyFromFavorite(company)
        }
    }

    override fun setLastPrice(company: Company) {
        chartView.setPrice(
            "${company.lastPrice} \u20BD"
        )
    }

    override fun setChangePrice(company: Company) {
        val color = if (company.changePrice > 0) {
            ColorCandle.UP
        } else {
            ColorCandle.DOWN
        }
        val changePriceFormat = company.changePrice.priceFormat(company.lastPrice)
        val changePercentFormat = company.changePricePercent.percentFormat()
        val changePrice = "$changePriceFormat ₽ ($changePercentFormat%)"
        chartView.setChangePrice(changePrice, color)
    }


}