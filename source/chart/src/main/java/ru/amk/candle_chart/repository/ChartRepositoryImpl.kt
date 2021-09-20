package ru.amk.candle_chart.repository

import io.reactivex.Single
import ru.amk.core.candle.Candle
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.company.Company
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val candleRepositoryCore: CandleRepositoryCore,
    private val favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore
) :
    ChartRepository {

    override fun getCandles(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<List<Candle>> =
        candleRepositoryCore.getCandleList(secId, dataFrom, dataTill)

    override fun addCompanyToFavorite(company: Company) {
        favoriteCompanyRepositoryCore.addFavoriteCompany(company)
    }

    override fun deleteCompanyFromFavorite(company: Company) {
        favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(company)
    }
}