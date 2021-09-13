package ru.amk.candle_chart.view

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.widget.Toast
import androidx.annotation.RequiresApi
import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.candle_chart.view.Paints.paintAxis
import ru.amk.candle_chart.view.Paints.paintAxisDottedLine
import ru.amk.candle_chart.view.Paints.paintText
import ru.amk.core.candle.Candle
import java.time.LocalDate

const val COUNT_OF_VALUE_Y_AXIS = 5
const val SEGMENT_LENGTH = 5f

class CandleChartViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CandleChartView, BaseView(context, attrs, defStyleAttr) {


    private var _heightPerValue: Double = 0.0
    private var _currentX: Int = 0
    private var _widthSize = 0


    private var _stepXAxis = 0f//Расстояние между подписями оси X

    private lateinit var _candleChartPresenter: CandleChartPresenter
    override fun setExpensePresenter(candleChartPresenter: CandleChartPresenter) {
        _candleChartPresenter = candleChartPresenter
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        _widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> {

                _stepXAxis = _widthPerView.toFloat()
                if (candleList.isNotEmpty()) {
                    _heightPerValue = _coordZeroY / _maxValueYAxis
                }
                setMeasuredDimension(_widthPerView * candleList.size, _heightView)
            }
        }
        _candleChartPresenter.scrollToLeft()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?) {

        if (candleList.isNotEmpty()) {
            _currentX = 0
            _heightPerValue = _coordZeroY / candleList.diff()

            canvas?.let { onDrawCoordinateGrid(canvas) }
            for ((index, item) in candleList.withIndex()) {
                canvas?.let {
                    onDrawXAxisSignatures(canvas, item, index)
                    onDrawShadow(canvas, item)
                    onDrawBody(canvas, item)
                }
                _currentX += _widthPerView
            }
        }
    }


    override fun getWidthView(): Int = _widthPerView * candleList.size

    private fun onDrawShadow(canvas: Canvas, candle: Candle) {
        val positionX = (_currentX + _widthPerView / 2).toFloat()


        val top = _coordZeroY - (_heightPerValue * (candle.maxPrice - candleList.min())).toFloat()
        val bottom =
            _coordZeroY - (_heightPerValue * (candle.minPrice - candleList.min())).toFloat()
        canvas.drawLine(positionX, top, positionX, bottom, candle.colorBody.paint)


    }

    private fun onDrawBody(canvas: Canvas, candle: Candle) {
        val left = (_currentX + _widthPerView / 2 - _widthPerView / 4).toFloat()
        val top = _coordZeroY - (_heightPerValue * (candle.openPrice - candleList.min())).toFloat()
        val right = (_currentX + _widthPerView / 2 + _widthPerView / 4).toFloat()
        val bottom =
            _coordZeroY - (_heightPerValue * (candle.closePrice - candleList.min())).toFloat()
        if (candle.openPrice == candle.closePrice) {
            canvas.drawLine(left, top, right, bottom, candle.colorBody.paint)
        } else {
            canvas.drawRect(left, top, right, bottom, candle.colorBody.paint)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDrawXAxisSignatures(canvas: Canvas, item: Candle, index: Int) {
        val positionX = (_currentX + _widthPerView / 2).toFloat()
        //Рисование сигнатуры на оси Х
        val startY = _coordZeroY - SEGMENT_LENGTH
        val stopY = _coordZeroY + SEGMENT_LENGTH
        canvas.drawLine(positionX, startY, positionX, stopY, paintAxis)
        canvas.drawLine(positionX, stopY, positionX, _coordEndYAxis, paintAxisDottedLine)
        if (index % 2 == 0) {
            canvas.drawText(item.date.convertDate(), positionX - 40f, stopY + 30f, paintText)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDrawCoordinateGrid(canvas: Canvas) {
        canvas.drawLine(_coordZeroX, _coordZeroY, _coordEndXAxis, _coordZeroY, paintAxis)//ось X

        for (item in 1..COUNT_OF_VALUE_Y_AXIS) {
            val startY = item * _stepYAxis
            val stopX = _coordZeroX
            //горизонтальный пунктир
            canvas.drawLine(stopX, startY, _coordEndXAxis, startY, paintAxisDottedLine)
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

@RequiresApi(Build.VERSION_CODES.O)
private fun String.convertDate(): String {
    val localDate = LocalDate.parse(this)
    return if (localDate.monthValue < 10) {
        "${localDate.dayOfMonth}.0${localDate.monthValue}"
    } else {
        "${localDate.dayOfMonth}.${localDate.monthValue}"
    }
}


