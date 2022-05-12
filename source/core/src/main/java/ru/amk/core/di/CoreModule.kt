package ru.amk.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.amk.core.candle.CandleRepositoryCore
import ru.amk.core.candle.CandleRepositoryCoreNetwork
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.company.CompanyRepositoryCoreNetwork
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCoreImpl
import ru.amk.core.moex_model.company.MoexCandleService
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexCandleServiceNetworkImpl
import ru.amk.core.three_line_break.ThreeLineRepository
import ru.amk.core.three_line_break.ThreeLineRepositoryNetwork
import ru.amk.core.utils.SchedulerProvider
import javax.inject.Singleton

@Module
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindMoexCandleServiceNetwork(moexCandleServiceNetwork: MoexCandleServiceNetworkImpl): MoexCandleServiceNetwork

    @Binds
    @Singleton
    abstract fun bindCandleRepositoryCore(candleRepositoryCore: CandleRepositoryCoreNetwork): CandleRepositoryCore

    @Binds
    @Singleton
    abstract fun bindThreeLineBreakRepository(threeLineRepositoryNetwork: ThreeLineRepositoryNetwork): ThreeLineRepository

    @Binds
    @Singleton
    abstract fun bindCompanyRepositoryCore(companyRepositoryCore: CompanyRepositoryCoreNetwork): CompanyRepositoryCore

    @Binds
    @Singleton
    abstract fun bindFavoriteCompanyRepositoryCore(favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCoreImpl): FavoriteCompanyRepositoryCore

    companion object {
        @Provides
        @Singleton
        fun provideSchedulerProvider(): SchedulerProvider =
            object : SchedulerProvider {
                override fun io(): Scheduler = Schedulers.io()

                override fun computation(): Scheduler = Schedulers.computation()

                override fun result(): Scheduler = AndroidSchedulers.mainThread()
            }
    }
}