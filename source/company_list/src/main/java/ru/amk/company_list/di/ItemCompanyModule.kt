package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.item.ItemCompanyPresenterImpl
import ru.amk.company_list.item.ItemView
import ru.amk.company_list.item.ItemViewImpl
import ru.amk.core.di.ActivityScope

@Module
abstract class ItemCompanyModule {


    @Binds
    @ActivityScope
    abstract fun bindItemView(itemView:ItemViewImpl):ItemView

    @Binds
    @ActivityScope
    abstract fun bindItemCompanyPresenter(itemCompanyPresenter: ItemCompanyPresenterImpl):ItemCompanyPresenter
}