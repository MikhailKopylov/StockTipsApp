package ru.amk.base_view_chart

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import ru.amk.base_view_chart.ChartValue.COUNT_OF_VALUE_Y_AXIS
import ru.amk.base_view_chart.ChartValue.OFFSET_AXIS_Y
import ru.amk.base_view_chart.ChartValue.SEGMENT_LENGTH
import ru.amk.base_view_chart.ChartValue.currentX
import ru.amk.base_view_chart.ChartValue.coordEndXAxis
import ru.amk.base_view_chart.ChartValue.coordEndYAxis
import ru.amk.base_view_chart.ChartValue.coordZeroX
import ru.amk.base_view_chart.ChartValue.coordZeroY
import ru.amk.base_view_chart.ChartValue.heightView
import ru.amk.base_view_chart.ChartValue.stepYAxis
import ru.amk.base_view_chart.ChartValue.widthPerView
import ru.amk.core.candle.Candle
import java.time.LocalDate
import java.util.*


abstract class BaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        heightView = (heightSize / 1.5).toInt()
        coordZeroY = heightView - OFFSET_AXIS_Y



        stepYAxis = coordZeroY / COUNT_OF_VALUE_Y_AXIS.toFloat()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        ChartValue.widthSize = MeasureSpec.getSize(widthMeasureSpec)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDrawXAxisSignatures(canvas: Canvas, date: String, index: Int) {
        val positionX = (currentX + widthPerView / 2).toFloat()
        //Рисование сигнатуры на оси Х
        val startY = coordZeroY - SEGMENT_LENGTH
        val stopY = coordZeroY + SEGMENT_LENGTH
        canvas.drawLine(positionX, startY, positionX, stopY, Paints.paintAxis)
        canvas.drawLine(positionX, stopY, positionX, coordEndYAxis, Paints.paintAxisDottedLine)
        if (index % 2 == 0) {
            canvas.drawText(date.convertDate(), positionX - 40f, stopY + 30f, Paints.paintText)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDrawCoordinateGrid(canvas: Canvas) {
        canvas.drawLine(coordZeroX, coordZeroY, coordEndXAxis, coordZeroY, Paints.paintAxis)//ось X
        canvas.drawLine(coordZeroX, 0f, coordEndXAxis, 0f, Paints.paintAxis)//ось X

        for (item in 1..COUNT_OF_VALUE_Y_AXIS) {
            val startY = item * stepYAxis
            val stopX = coordZeroX
            //горизонтальный пунктир
            canvas.drawLine(stopX, startY, coordEndXAxis, startY, Paints.paintAxisDottedLine)
        }
    }

}


fun List<Candle>.diffCandlestick(): Double {
    if (this.isEmpty()) return -1.0
    val max = this.map { it.maxPrice }.maxOf { it }
    val min = this.map { it.minPrice }.minOf { it }
    return diff(max, min)
}

fun diff(max: Double, min: Double): Double = (max + max / 50) - (min - min / 50)

fun Double.roundForAxisSignature(): Double {
    return when (this) {
        in 0.0..1.0 -> {
            String.format(Locale.US, "%.4f", this).toDouble()
        }
        in 1.0..10.0 -> {
            String.format(Locale.US, "%.2f", this).toDouble()
        }
        in 10.0..100.0 -> {
            String.format(Locale.US, "%.1f", this).toDouble()
        }
        else -> {
            String.format(Locale.US, "%.0f", this).toDouble()
        }
    }
}

internal fun List<Candle>.min(): Double {
    return if (this.isNotEmpty()) {
        val min = this.map { it.minPrice }.minOf { it }
        (min - min / 50)
    } else {
        0.0
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertDate(): String {
    val localDate = LocalDate.parse(this)
    return if (localDate.monthValue < 10) {
        "${localDate.dayOfMonth}.0${localDate.monthValue}"
    } else {
        "${localDate.dayOfMonth}.${localDate.monthValue}"
    }
}

