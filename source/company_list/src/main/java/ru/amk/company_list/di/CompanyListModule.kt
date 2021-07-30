package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListPresenterImpl
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.company_list.repository.CompanyListRepositoryImpl

@Module
abstract class CompanyListModule {

    companion object {
        @Provides
        fun provideCompanyListPresenter(
            view: CompanyListViewImpl,
            companyListRepository: CompanyListRepositoryImpl
        ): CompanyListPresenter =
            CompanyListPresenterImpl(companyListRepository, view)
    }


    @Binds
    abstract fun bindCompanyListRepository(companyListRepository: CompanyListRepositoryImpl): CompanyListRepository


}