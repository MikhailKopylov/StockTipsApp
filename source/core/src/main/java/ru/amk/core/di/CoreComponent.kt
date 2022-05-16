package ru.amk.core.di

import dagger.Component
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import ru.amk.core.favorite_company.room.FavoriteCompanyDAO
import ru.amk.core.three_line_break.ThreeLineRepository
import ru.amk.core.utils.SchedulerProvider
import javax.inject.Singleton

@Component(
    dependencies = [AppProvider::class],
    modules = [RetrofitModule::class, CoreModule::class, DatabaseModule::class]
)
@Singleton
interface CoreComponent {

    fun getCandleRepositoryCore(): CandleRepositoryCore
    fun getThreeLineRepository(): ThreeLineRepository
    fun getCompanyListRepositoryCore(): CompanyRepositoryCore
    fun getFavoriteCompanyListRepositoryCore(): FavoriteCompanyRepositoryCore
    fun getFavoriteCompanyDAO(): FavoriteCompanyDAO
    fun getSchedulerProvider(): SchedulerProvider

}