package ru.amk.company_list.item

import ru.amk.core.candle.ColorCandle
import ru.amk.core.company.Company

interface ItemView {
    fun setCompanyName(name:String)
    fun setCompanySetId(secId:String)
    fun openCandleScreen(company: Company, isFavorite:Boolean)
    fun setFavorite(favorite: Boolean)
    fun setPriceChange(changePrice:String, colorText:ColorCandle)
    fun setPercentChange(changePercent:String, colorText:ColorCandle)
}