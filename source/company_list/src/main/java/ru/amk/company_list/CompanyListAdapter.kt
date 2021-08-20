package ru.amk.company_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.amk.company_list.di.DaggerItemCompanyComponent
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject


class CompanyListAdapter @Inject constructor(
    val companyListPresenter: CompanyListPresenter,
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    init {
        companyListPresenter.onViewCreated()
    }


    inner class CompanyViewHolder(itemView: View) :
        BaseViewHolder(itemView), View.OnClickListener {

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
            itemCompanyPresenter.onClickItem(adapterPosition)
            Toast.makeText(v.context, "$adapterPosition", Toast.LENGTH_SHORT).show()
        }

        override fun onBind(position: Int) {
            itemCompanyPresenter.onBind(position)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            R.layout.item_empty -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty, parent, false)
                EmptyViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_company, parent, false)
                CompanyViewHolder(itemView)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (companyListPresenter.getCount() == 0) {
            R.layout.item_empty
        } else {
            R.layout.item_company
        }
    }

    override fun onBindViewHolder(holderCompany: BaseViewHolder, position: Int) {
        holderCompany.onBind(position)

    }

    override fun getItemCount(): Int {
        return if (companyListPresenter.getCount() == 0) {
            1
        } else {
            companyListPresenter.getCount()
        }
    }


}