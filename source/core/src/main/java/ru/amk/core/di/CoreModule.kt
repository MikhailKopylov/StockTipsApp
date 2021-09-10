package ru.amk.core.di

import dagger.Binds
import dagger.Module
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.candle.CandleRepositoryCoreNetwork
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.company.CompanyRepositoryCoreNetwork
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCoreImpl
import ru.amk.core.moex_model.company.MoexCandleService
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexCandleServiceNetworkImpl
import javax.inject.Singleton

@Module
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindMoexCandleServiceNetwork(moexCandleServiceNetwork: MoexCandleServiceNetworkImpl): MoexCandleServiceNetwork

    @Binds
    @Singleton
    abstract fun bindCandleRepositoryCore(candleRepositoryCore: CandleRepositoryCoreNetwork):CandleRepositoryCore

    @Binds
    @Singleton
    abstract fun bindCompanyRepositoryCore(companyRepositoryCore: CompanyRepositoryCoreNetwork):CompanyRepositoryCore

    @Binds
    @Singleton
    abstract fun bindFavoriteCompanyRepositoryCore(favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCoreImpl):FavoriteCompanyRepositoryCore

}