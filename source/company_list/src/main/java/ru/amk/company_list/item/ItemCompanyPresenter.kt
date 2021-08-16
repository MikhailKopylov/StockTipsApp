package ru.amk.company_list.item

import android.content.Context

interface ItemCompanyPresenter {

    fun onBind(position: Int)
    fun onClickItem(context: Context, adapterPosition: Int)


}