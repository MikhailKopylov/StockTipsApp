package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.company_list.RefreshDataView
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListPresenterImpl
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.company_list.repository.CompanyListRepositoryImpl
import ru.amk.core.di.ActivityScope
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCoreImpl

@Module
abstract class CompanyListModule {

    companion object {
        @Provides
        @ActivityScope
        fun provideCompanyListPresenter(
            view: CompanyListViewImpl,
            companyListRepository: CompanyListRepositoryImpl,
            favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCoreImpl,
            refreshView: RefreshDataView
        ): CompanyListPresenter =
            CompanyListPresenterImpl(companyListRepository, view, favoriteCompanyRepositoryCore, refreshView)
    }


    @Binds
    @ActivityScope
    abstract fun bindCompanyListRepository(companyListRepository: CompanyListRepositoryImpl): CompanyListRepository


}