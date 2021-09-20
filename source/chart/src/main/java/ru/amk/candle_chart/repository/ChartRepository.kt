package ru.amk.candle_chart.repository

import io.reactivex.Single
import ru.amk.core.candle.Candle
import ru.amk.core.company.Company

interface ChartRepository {

    fun getCandles(secId:String, dataFrom:String, dataTill:String): Single<List<Candle>>
    fun addCompanyToFavorite(company: Company)
    fun deleteCompanyFromFavorite(company: Company)
}