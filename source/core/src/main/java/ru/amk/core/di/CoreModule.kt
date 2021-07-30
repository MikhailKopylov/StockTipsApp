package ru.amk.core.di

import dagger.Binds
import dagger.Module
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.candle.CandleRepositoryCoreNetwork
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.company.CompanyRepositoryCoreNetwork
import ru.amk.core.moex_model.company.MoexCandleService
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexCandleServiceNetworkImpl

@Module
abstract class CoreModule {

    @Binds
    abstract fun bindMoexCandleServiceNetwork(moexCandleServiceNetwork: MoexCandleServiceNetworkImpl): MoexCandleServiceNetwork

    @Binds
    abstract fun bindCandleRepositoryCore(candleRepositoryCore: CandleRepositoryCoreNetwork):CandleRepositoryCore

    @Binds
    abstract fun bindCompanyRepositoryCore(companyRepositoryCore: CompanyRepositoryCoreNetwork):CompanyRepositoryCore

}