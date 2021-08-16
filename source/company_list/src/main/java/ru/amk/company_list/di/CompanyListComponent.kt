package ru.amk.company_list.di

import dagger.BindsInstance
import dagger.Component
import ru.amk.company_list.CompanyListActivity
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.core.di.ActivityScope
import ru.amk.core.mediator.AppProvider
import ru.amk.core.di.CoreComponent
import ru.amk.core.mediator.ProvidesFacade

@Component(
    dependencies = [CoreComponent::class, ProvidesFacade::class],
    modules = [CompanyListModule::class, ItemCompanyModule::class]
)
@ActivityScope
interface CompanyListComponent {

    fun inject(activity: CompanyListActivity)

    @Component.Builder
    interface CompanyListComponentBuilder {

        fun build(): CompanyListComponent

        @BindsInstance
        fun companyListView(view: CompanyListViewImpl): CompanyListComponentBuilder

        fun coreComponent(coreComponent: CoreComponent): CompanyListComponentBuilder

        fun providerFacade(providerFacade: ProvidesFacade): CompanyListComponentBuilder


    }
}