package ru.amk.candle_chart.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import ru.amk.core.candle.Candle


interface AxisYView {

    fun drawAxisY(candles: List<Candle>)
}


class AxisYViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AxisYView, BaseView(context, attrs, defStyleAttr) {

    private var _defaultWidth = context.resources.displayMetrics.density * 25
    private var _signatureCoordX = 1f
    private var _signatureCoordY = 1f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        _coordZeroX = _defaultWidth - 2
        setMeasuredDimension(_defaultWidth.toInt(), _heightView)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            canvas.drawLine(
                _coordZeroX,
                _coordZeroY,
                _coordZeroX,
                _coordEndYAxis,
                Paints.paintAxis
            )//ось Y
            for (item in 1..COUNT_OF_VALUE_Y_AXIS) {
                val startY = item * _stepYAxis
                val startX = _coordZeroX - SEGMENT_LENGTH
                val stopX = _coordZeroX + SEGMENT_LENGTH
                canvas.drawLine(startX, startY, stopX, startY, Paints.paintAxis)
                canvas.drawLine(stopX, startY, _coordEndXAxis, startY, Paints.paintAxisDottedLine)
                canvas.drawText(
                    "${(_maxValueYAxis - (_stepValueYAxis * item) + candleList.min()).roundForAxisSignature()}",
                    _signatureCoordX,
                    startY,
                    Paints.paintText
                )
            }
        }
    }

    override fun drawAxisY(candles: List<Candle>) {
        super.candleList = candles
        requestLayout()
        invalidate()
    }
}