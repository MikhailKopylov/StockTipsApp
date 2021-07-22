package ru.amk.core.candle

import android.graphics.Color
import android.graphics.Paint

enum class ColorCandle(val paint: Paint) {

    BLACK(Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
    }),

    WHITE(Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 10f
    })

}