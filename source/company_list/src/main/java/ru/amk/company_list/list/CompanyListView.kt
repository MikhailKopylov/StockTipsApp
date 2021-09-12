package ru.amk.company_list.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.amk.company_list.FavoriteCompany

interface CompanyListView {

    fun notifyAllDataChange(newListCompany: List<FavoriteCompany>)
    fun notifyDiffDataChange(oldListCompany:List<FavoriteCompany>, newListCompany: List<FavoriteCompany>)
}


class CompanyListViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr), CompanyListView {


    @SuppressLint("NotifyDataSetChanged")
    override fun notifyAllDataChange(newListCompany: List<FavoriteCompany>) {
        adapter?.notifyDataSetChanged()
    }

    override fun notifyDiffDataChange(
        oldListCompany: List<FavoriteCompany>,
        newListCompany: List<FavoriteCompany>
    ) {
        val comDiffUtil = CompanyDiffUtil(oldListCompany, newListCompany)
        val companyDiffResult = DiffUtil.calculateDiff(comDiffUtil, true)
        adapter?.let{companyDiffResult.dispatchUpdatesTo(it)}
    }


}