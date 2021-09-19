package ru.amk.core

import ru.amk.core.candle.Candle

fun List<Candle>.minCandleList(): Double {
    return if (this.isNotEmpty()) {
        val min = this.map { it.minPrice }.minOf { it }
        (min - min / 50)
    } else {
        0.0
    }
}
