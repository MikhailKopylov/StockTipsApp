package ru.amk.core

import ru.amk.core.candle.Candle
import kotlin.math.abs

fun List<Candle>.minCandleList(): Double {
    return if (this.isNotEmpty()) {
        val min = this.map { it.minPrice }.minOf { it }
        (min - min / 50)
    } else {
        0.0
    }
}

fun Double.priceFormat(price: Double): String {
    val absDouble = abs(this)
    val digits = when (price) {
        in 0.0..1.0 -> 6
        in 1.0..100.0 -> 3
        else -> 2
    }
    val sign = when {
        this < 0.0 -> '-'
        else -> "+"
    }
    return "$sign %.${digits}f".format(absDouble)
}

fun Double.percentFormat(): String {
    val absDouble = abs(this)
    val sign = when {
        this < 0.0 -> '-'
        else -> "+"
    }
    return "$sign %.${2}f".format(absDouble)
}
