package ru.amk.core.candle

import android.graphics.Color
import android.graphics.Paint

enum class ColorCandle(val paint: Paint) {

    DOWN(Paint().apply {
        color = Color.RED
        strokeWidth = 5f
    }),

    UP(Paint().apply {
        color = Color.rgb(0, 128, 0)
        strokeWidth = 5f
    })
}
