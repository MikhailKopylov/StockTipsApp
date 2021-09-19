package ru.amk.candle.view

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import ru.amk.base_view_chart.*
import ru.amk.base_view_chart.ChartValue.COUNT_OF_VALUE_Y_AXIS
import ru.amk.base_view_chart.ChartValue.currentX
import ru.amk.base_view_chart.ChartValue.heightPerValue
import ru.amk.base_view_chart.ChartValue.stepXAxis
import ru.amk.base_view_chart.ChartValue.coordEndXAxis
import ru.amk.base_view_chart.ChartValue.coordZeroX
import ru.amk.base_view_chart.ChartValue.coordZeroY
import ru.amk.base_view_chart.ChartValue.heightView
import ru.amk.base_view_chart.ChartValue.maxValueYAxis
import ru.amk.base_view_chart.ChartValue.minValueYAxis
import ru.amk.base_view_chart.ChartValue.widthPerView
import ru.amk.candle.CandleChartPresenter
import ru.amk.core.candle.Candle
import ru.amk.core.minCandleList


class CandlestickViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CandlestickView, BaseView(context, attrs, defStyleAttr) {


    private var candleList: List<Candle> = emptyList()
    private lateinit var _candleChartPresenter: CandleChartPresenter
    override fun setCandlestickPresenter(candlestickPresenter: CandleChartPresenter) {
        _candleChartPresenter = candlestickPresenter
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        maxValueYAxis = candleList.diffCandlestick()
        minValueYAxis = candleList.minCandleList()
        ChartValue.stepValueYAxis = maxValueYAxis / COUNT_OF_VALUE_Y_AXIS
        coordEndXAxis = widthPerView * candleList.size - coordZeroX
        _candleChartPresenter.scrollToLeft()


        when (widthMode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> {

                stepXAxis = widthPerView.toFloat()
                if (candleList.isNotEmpty()) {
                    heightPerValue = coordZeroY / maxValueYAxis
                }
                setMeasuredDimension(widthPerView * candleList.size, heightView)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?) {

        if (candleList.isNotEmpty()) {
            currentX = 0
            heightPerValue = coordZeroY / candleList.diffCandlestick()

            canvas?.let { onDrawCoordinateGrid(canvas) }
            for ((index, item) in candleList.withIndex()) {
                canvas?.let {
                    onDrawXAxisSignatures(canvas, item.date, index)
                    onDrawShadow(canvas, item)
                    onDrawBody(canvas, item)
                }
                currentX += widthPerView
            }
        }
    }

    override fun getWidthView(): Int = widthPerView * candleList.size

    override fun isVisible(): Boolean = isVisible

    private fun onDrawShadow(canvas: Canvas, candle: Candle) {
        val positionX = (currentX + widthPerView / 2).toFloat()


        val top = coordZeroY - (heightPerValue * (candle.maxPrice - minValueYAxis)).toFloat()
        val bottom =
            coordZeroY - (heightPerValue * (candle.minPrice - minValueYAxis)).toFloat()
        canvas.drawLine(positionX, top, positionX, bottom, candle.colorBody.paint)


    }

    private fun onDrawBody(canvas: Canvas, candle: Candle) {
        val left = (currentX + widthPerView / 2 - widthPerView / 4).toFloat()
        val top = coordZeroY - (heightPerValue * (candle.openPrice - minValueYAxis)).toFloat()
        val right = (currentX + widthPerView / 2 + widthPerView / 4).toFloat()
        val bottom =
            coordZeroY - (heightPerValue * (candle.closePrice - minValueYAxis)).toFloat()
        if (candle.openPrice == candle.closePrice) {
            canvas.drawLine(left, top, right, bottom, candle.colorBody.paint)
        } else {
            canvas.drawRect(left, top, right, bottom, candle.colorBody.paint)
        }
    }


    override fun drawCandles(candles: List<Candle>) {
        candleList = candles
        requestLayout()
        invalidate()
    }

    override fun showNoData() {
        Toast.makeText(this.context, "Error! No data!", Toast.LENGTH_SHORT).show()
    }

}






