package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListPresenterImpl
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.company_list.repository.CompanyListRepositoryImpl
import ru.amk.core.di.ActivityScope
import ru.amk.core.mediator.CandleChartMediator

@Module
abstract class CompanyListModule {

    companion object {
        @Provides
        @ActivityScope
        fun provideCompanyListPresenter(
            view: CompanyListViewImpl,
            companyListRepository: CompanyListRepositoryImpl,
            mediator: CandleChartMediator
        ): CompanyListPresenter =
            CompanyListPresenterImpl(companyListRepository, view, mediator)
    }

    @Binds
    @ActivityScope
    abstract fun bindCompanyListRepository(companyListRepository: CompanyListRepositoryImpl): CompanyListRepository


}