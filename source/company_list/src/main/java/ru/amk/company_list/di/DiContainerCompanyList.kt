package ru.amk.company_list.di

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.amk.company_list.CompanyListAdapter
import ru.amk.company_list.R
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.item.ItemCompanyPresenterImpl
import ru.amk.company_list.item.ItemView
import ru.amk.company_list.item.ItemViewImpl
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListPresenterImpl
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.repository.CompanyRepository
import ru.amk.company_list.repository.CompanyRepositoryImpl
import ru.amk.core.CompanyRepositoryCoreNetwork
import ru.amk.core.di.DiContainerCore

class DiContainerCompanyList(context: Context) {
    private val companyRepository: CompanyRepository = CompanyRepositoryImpl(
        CompanyRepositoryCoreNetwork(DiContainerCore().moexCandleService)
    )

    private lateinit var _rootView:View
    private lateinit var itemView:ItemView
    lateinit var itemCompanyPresenter: ItemCompanyPresenter

    fun rootView(parent:ViewGroup):View {
        _rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company, parent, false)
        itemView= ItemViewImpl(_rootView)

        itemCompanyPresenter = ItemCompanyPresenterImpl(itemView,companyListPresenter)
        return _rootView
    }

    val companyListRW: CompanyListViewImpl = (context as Activity).findViewById(R.id.company_list_rw)
    private val companyListPresenter:CompanyListPresenter = CompanyListPresenterImpl(companyRepository, companyListRW)

    val companyListAdapter: CompanyListAdapter = CompanyListAdapter(companyListPresenter, this)

}






