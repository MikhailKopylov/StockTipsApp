package ru.amk.company_list.di

import android.view.View
import dagger.BindsInstance
import dagger.Component
import ru.amk.company_list.CompanyListAdapter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.core.di.ActivityScope
import ru.amk.core.di.CoreComponent

@Component(
    dependencies = [CoreComponent::class],
    modules = [ItemCompanyModule::class]
)
@ActivityScope
interface ItemCompanyComponent {

    fun inject(holder: CompanyListAdapter.CompanyViewHolder)

    @Component.Builder
    interface CompanyItemComponentBuilder {

        fun build(): ItemCompanyComponent

        @BindsInstance
        fun itemView(itemView: View): CompanyItemComponentBuilder

        @BindsInstance
        fun companyListPresenter(companyListPresenter: CompanyListPresenter): CompanyItemComponentBuilder

        fun coreComponent(coreComponent: CoreComponent): CompanyItemComponentBuilder
    }
}