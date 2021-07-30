package ru.amk.core.di

import dagger.Component
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.company.CompanyRepositoryCore
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, CoreModule::class])
@Singleton
interface CoreComponent {

    fun getCandleRepositoryCore():CandleRepositoryCore
    fun getCompanyListRepositoryCore():CompanyRepositoryCore
}