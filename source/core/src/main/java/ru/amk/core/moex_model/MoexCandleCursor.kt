package ru.amk.core.moex_model

data class MoexCandleCursor(
    val index:Int,
    val total:Int,
    val pageSize:Int,
)