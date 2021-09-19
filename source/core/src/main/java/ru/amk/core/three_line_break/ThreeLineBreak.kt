package ru.amk.core.three_line_break

import ru.amk.core.candle.ColorCandle


data class ThreeLineBreak(
    val maxPrice:Double,
    val minPrice:Double,
    val date:String,
    val colorBody:ColorCandle
)