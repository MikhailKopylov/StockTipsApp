package ru.amk.company_list.item

interface ItemView {
    fun setCompanyName(name:String)
    fun setCompanySetId(secId:String)
    fun openCandleScreen(secId:String, dateTill:String)
}