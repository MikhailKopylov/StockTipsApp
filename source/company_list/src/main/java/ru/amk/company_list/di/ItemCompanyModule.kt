package ru.amk.company_list.di

import dagger.Binds
import dagger.Module
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.item.ItemCompanyPresenterImpl
import ru.amk.company_list.item.ItemView
import ru.amk.company_list.item.ItemViewImpl

@Module
abstract class ItemCompanyModule {


    @Binds
    abstract fun bindItemView(itemView:ItemViewImpl):ItemView

    @Binds
    abstract fun bindItemCompanyPresenter(itemCompanyPresenter: ItemCompanyPresenterImpl):ItemCompanyPresenter
}