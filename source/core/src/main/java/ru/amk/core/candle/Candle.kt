package ru.amk.core.candle

data class Candle(
    val maxPrice: Double,
    val minPrice: Double,
    val openPrice: Double,
    val closePrice: Double,
    val date: String,
    val colorBody: ColorCandle
)
