package ru.amk.core.di

import dagger.Component
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import ru.amk.core.favorite_company.room.FavoriteCompanyDAO
import javax.inject.Singleton

@Component(
    dependencies = [AppProvider::class],
    modules = [RetrofitModule::class, CoreModule::class, DatabaseModule::class])
@Singleton
interface CoreComponent {

    fun getCandleRepositoryCore():CandleRepositoryCore
    fun getCompanyListRepositoryCore():CompanyRepositoryCore
    fun getFavoriteCompanyListRepositoryCore():FavoriteCompanyRepositoryCore
    fun getFavoriteCompanyDAO():FavoriteCompanyDAO

}