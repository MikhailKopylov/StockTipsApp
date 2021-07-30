package ru.amk.company_list.di

import dagger.BindsInstance
import dagger.Component
import ru.amk.company_list.CompanyListActivity
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.core.di.AppProvider
import ru.amk.core.di.CoreComponent

@Component(
    dependencies = [CoreComponent::class, AppProvider::class],
    modules = [CompanyListModule::class, ItemCompanyModule::class]
)
interface CompanyListComponent {

    fun inject(activity: CompanyListActivity)

    @Component.Builder
    interface CompanyListComponentBuilder {

        fun build(): CompanyListComponent

        @BindsInstance
        fun companyListView(view:CompanyListViewImpl): CompanyListComponentBuilder

        fun coreComponent(coreComponent: CoreComponent): CompanyListComponentBuilder

        fun appProvider(appProvider: AppProvider): CompanyListComponentBuilder
    }
}