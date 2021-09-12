package ru.amk.company_list.list

import androidx.recyclerview.widget.DiffUtil
import ru.amk.company_list.FavoriteCompany

class CompanyDiffUtil(
    private val oldList: List<FavoriteCompany>,
    private val newList: List<FavoriteCompany>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldList[oldItemPosition].company.secId == newList[oldItemPosition].company.secId) &&
                (oldList[oldItemPosition].isFavorite == newList[oldItemPosition].isFavorite)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldList[oldItemPosition].company.shortName == newList[oldItemPosition].company.shortName) &&
                (oldList[oldItemPosition].company.date == newList[oldItemPosition].company.date)

}