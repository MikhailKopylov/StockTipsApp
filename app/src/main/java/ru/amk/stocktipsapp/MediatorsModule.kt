package ru.amk.stocktipsapp

import dagger.Binds
import dagger.Module
import ru.amk.candle_chart.CandleChartMediatorImpl
import ru.amk.company_list.CompanyListMediatorImpl
import ru.amk.core.mediator.CandleChartMediator
import ru.amk.core.mediator.CompanyListMediator

@Module
interface MediatorsModule {

    @Binds
    fun bindCompanyListScreen(companyListMediator: CompanyListMediatorImpl): CompanyListMediator

    @Binds
    fun bindCandleChartMediator(candleChartMediator: CandleChartMediatorImpl): CandleChartMediator
}