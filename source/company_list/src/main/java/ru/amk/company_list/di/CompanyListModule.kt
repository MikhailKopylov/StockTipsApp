package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.company_list.ActivityView
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListPresenterImpl
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.list.interactors.*
import ru.amk.core.di.ActivityScope
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCoreImpl
import javax.inject.Named

@Module
abstract class CompanyListModule {

    companion object {
        @Provides
        @ActivityScope
        fun provideCompanyListPresenter(
            sortInteractor: FilterCompanyInteractor,
            view: CompanyListViewImpl,
            favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCoreImpl,
            refreshView: ActivityView,
        ): CompanyListPresenter =
            CompanyListPresenterImpl(sortInteractor, view, favoriteCompanyRepositoryCore, refreshView)

    }



    @Binds
    @ActivityScope
    @Named("favorite")
    abstract fun bindFavoriteCompanyInteractor(favoriteCompanyInteractor: FavoriteCompanyInteractor): CompanyInteractor

    @Binds
    @ActivityScope
    @Named("sort")
    abstract fun bindSortCompanyInteractor(sortCompanyInteractor: SortCompanyInteractor): CompanyInteractor


    @Binds
    @ActivityScope
    @Named("filter")
    abstract fun bindFilterCompanyInteractor(filterCompanyInteractor: FilterCompanyInteractor): CompanyInteractor

}