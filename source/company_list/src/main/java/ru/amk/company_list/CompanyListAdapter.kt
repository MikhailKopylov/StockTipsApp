package ru.amk.company_list

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.amk.company_list.di.DiContainerCompanyList
import ru.amk.company_list.item.ItemCompanyPresenter
import ru.amk.company_list.list.CompanyListPresenter

class CompanyListAdapter(
    private val companyListPresenter: CompanyListPresenter,
    private val diContainer: DiContainerCompanyList
) :
    RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {
    init {
        companyListPresenter.onViewCreated()
    }

    class CompanyViewHolder(itemView: View, val itemCompanyPresenter: ItemCompanyPresenter) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            Toast.makeText(v.context, "$adapterPosition", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val rootView = diContainer.rootView(parent)
        return CompanyViewHolder(rootView, diContainer.itemCompanyPresenter)
    }

    override fun onBindViewHolder(holderCompany: CompanyViewHolder, position: Int) {
        holderCompany.itemCompanyPresenter.onBind(position)
    }

    override fun getItemCount(): Int = companyListPresenter.getCount()


}