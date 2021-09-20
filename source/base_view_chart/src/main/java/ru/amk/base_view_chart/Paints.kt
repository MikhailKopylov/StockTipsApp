package ru.amk.base_view_chart

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint

object Paints {
    val paintAxis: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
    }

    val paintAxisDottedLine = Paint().apply {
        color = Color.DKGRAY
        style = Paint.Style.FILL
        strokeWidth = 1f
        pathEffect = DashPathEffect(floatArrayOf(5f, 15f), 0f)
    }

    val paintText = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 32f
    }
}