package ru.amk.base_view_chart

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import ru.amk.base_view_chart.ChartValue.COUNT_OF_VALUE_Y_AXIS
import ru.amk.base_view_chart.ChartValue.SEGMENT_LENGTH
import ru.amk.base_view_chart.ChartValue.coordEndYAxis
import ru.amk.base_view_chart.ChartValue.coordZeroX
import ru.amk.base_view_chart.ChartValue.coordZeroY
import ru.amk.base_view_chart.ChartValue.heightView
import ru.amk.base_view_chart.ChartValue.maxValueYAxis
import ru.amk.base_view_chart.ChartValue.stepValueYAxis
import ru.amk.base_view_chart.ChartValue.stepYAxis
import ru.amk.core.candle.Candle


interface AxisYView {

    fun drawAxisY(candles: List<Candle>)

}


class AxisYViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AxisYView, BaseView(context, attrs, defStyleAttr) {

    private var _defaultWidth = context.resources.displayMetrics.density * 50
    private var _signatureCoordX = 1f
    private var _signatureCoordY = 1f

    private var _minValue:Double = 0.0
    private var _stepNextValue:Double = 0.0

    private var _candleList:List<Candle> = emptyList()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        coordZeroX =  2f
        setMeasuredDimension(_defaultWidth.toInt(), heightView)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            canvas.drawLine(
                coordZeroX,
                coordZeroY,
                coordZeroX,
                coordEndYAxis,
                Paints.paintAxis
            )//ось Y
            for (item in 1..COUNT_OF_VALUE_Y_AXIS) {
                val startY = item * stepYAxis
                val startX = coordZeroX - SEGMENT_LENGTH
                val stopX = coordZeroX + SEGMENT_LENGTH
                canvas.drawLine(startX, startY, stopX, startY, Paints.paintAxis)
                canvas.drawText(
                    "${(maxValueYAxis - (stepValueYAxis * item) + _candleList.min()).roundForAxisSignature()}",
                    _signatureCoordX + 15f,
                    startY,
                    Paints.paintText
                )
            }
        }
    }

    override fun drawAxisY(candles: List<Candle>) {
        _candleList = candles
        requestLayout()
        invalidate()
    }




}