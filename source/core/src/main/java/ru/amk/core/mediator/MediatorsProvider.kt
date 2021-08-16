package ru.amk.core.mediator

interface MediatorsProvider {

    fun provideCandleChartMediator(): CandleChartMediator

    fun provideCompanyListMediator(): CompanyListMediator
}