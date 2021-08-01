package ru.amk.company_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.amk.company_list.di.DaggerItemCompanyComponent
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject

class CompanyListAdapter @Inject constructor(
    val companyListPresenter: CompanyListPresenter,
) :
    RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    init {
        companyListPresenter.onViewCreated()
    }

    inner class CompanyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        @Inject
        lateinit var itemCompanyPresenter: ItemCompanyPresenter

        init {
            itemView.setOnClickListener(this)
            DaggerItemCompanyComponent.builder()
                .companyListPresenter(companyListPresenter)
                .coreComponent(DaggerCoreComponent.create())
                .itemView(itemView)
                .build().inject(this)
        }
        override fun onClick(v: View) {
            itemCompanyPresenter.onClickItem(itemView.context, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company, parent, false)
        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holderCompany: CompanyViewHolder, position: Int) {
        holderCompany.itemCompanyPresenter.onBind(position)
    }

    override fun getItemCount(): Int = companyListPresenter.getCount()


}