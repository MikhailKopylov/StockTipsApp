package ru.amk.core.moex_model.xml.history_cursor

data class RowHistory(
    val index:Int,
    val total: Int,
    val pageSize:Int,
)
