package ru.amk.core.moex_model.company

data class MoexCandleCursor(
    val index:Int,
    val total:Int,
    val pageSize:Int,
)