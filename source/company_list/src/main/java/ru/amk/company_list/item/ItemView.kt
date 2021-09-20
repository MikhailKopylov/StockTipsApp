package ru.amk.company_list.item

import ru.amk.core.company.Company

interface ItemView {
    fun setCompanyName(name:String)
    fun setCompanySetId(secId:String)
    fun openCandleScreen(company: Company, isFavorite:Boolean)
    fun setFavorite(favorite: Boolean)
}