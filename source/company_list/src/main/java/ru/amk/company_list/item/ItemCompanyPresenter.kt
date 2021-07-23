package ru.amk.company_list.item

interface ItemCompanyPresenter {

    fun onBind(position: Int)
    fun onClickItem(adapterPosition: Int)


}