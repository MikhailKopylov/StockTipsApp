package ru.amk.candle_chart.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ru.amk.core.candle.Candle
import java.util.*

abstract class BaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var candleList: List<Candle> = listOf()


    protected var _coordZeroX = 0f// начало оси X
    protected var _coordEndXAxis = 0f//Конец оси X
    protected var _stepYAxis = 0f//Расстояние между сигнатурами оси Y
    protected var _maxValueYAxis = 0.0//максимально значение по оси Y
    protected var _stepValueYAxis = _maxValueYAxis / COUNT_OF_VALUE_Y_AXIS//шаг значение по оси
    protected var _heightView = 0
    protected val _widthPerView: Int = 100
    protected var _coordZeroY = 1f// начало оси Y
    protected var _coordEndYAxis = 0f//Конец оси Y

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        _heightView = heightSize / 2
        _coordZeroY = _heightView - 25f
        _maxValueYAxis = candleList.diff()
        _stepValueYAxis = _maxValueYAxis / COUNT_OF_VALUE_Y_AXIS
        _coordEndXAxis = _widthPerView * candleList.size - _coordZeroX
        _stepYAxis = _coordZeroY / COUNT_OF_VALUE_Y_AXIS.toFloat()

    }

}


internal fun List<Candle>.diff(): Double {
    if (this.isEmpty()) return -1.0
    val max = this.map { it.maxPrice }.maxOf { it }
    val min = this.map { it.minPrice }.minOf { it }
    return (max + max / 50) - (min - min / 50)
}

internal fun Double.roundForAxisSignature(): Double {
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

