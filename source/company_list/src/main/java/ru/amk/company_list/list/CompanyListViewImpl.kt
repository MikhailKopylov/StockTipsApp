package ru.amk.company_list.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import ru.amk.core.company.Company

class CompanyListViewImpl  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), CompanyListView{


    @SuppressLint("NotifyDataSetChanged")
    override fun notifyAllDataChange(newListCompany: List<Company>) {
        adapter?.notifyDataSetChanged()
    }


}