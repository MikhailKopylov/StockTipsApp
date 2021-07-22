package ru.amk.candle_chart.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import ru.amk.core.candle.Candle

class CandleChartViewImpl :CandleChartView, View{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    private var candleList: List<Candle> = listOf()

    private var heightPerValue: Int = 0
    private var widthPerView: Int = 150
    private var currentX: Int = 0
    private val paint: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> {
                setMeasuredDimension(widthPerView * candleList.size , heightSize * 10)
//                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {

    if(candleList.isNotEmpty()) {
        widthPerView = /*width / candleList.size*/ 150
        currentX = 0
        heightPerValue = height / (candleList.max()).toInt()

        for (item in candleList) {
//            val left = currentX.toFloat()
//            val top = (height - heightPerValue * item).toFloat()
//            val right = (currentX + widthPerView).toFloat()
//            val bottom = height.toFloat()
//            canvas?.drawRect(left, top, right, bottom, paint)
            canvas?.let {
                drawCoordinateAxis(canvas)
                onDrawShadow(canvas, item)
                onDrawBody(canvas, item)
            }

            currentX += widthPerView

        }
    }

    }

    private fun onDrawShadow(canvas: Canvas, candle: Candle) {
        val positionX = (currentX + widthPerView / 2).toFloat()
        val top = (height - heightPerValue * candle.maxPrice).toFloat()
        val bottom = (height - heightPerValue * candle.minPrice).toFloat()
        canvas.drawLine(positionX, top, positionX, bottom, paint)
    }

    private fun onDrawBody(canvas: Canvas, candle: Candle) {
        val left = (currentX + widthPerView / 2 - widthPerView / 8).toFloat()
        val top = (height - heightPerValue * candle.openPrice).toFloat()
        val right = (currentX + widthPerView / 2 + widthPerView / 8).toFloat()
        val bottom = (height - heightPerValue * candle.closePrice).toFloat()
        canvas.drawRect(left, top, right, bottom, candle.colorBody.paint)
    }

    private fun drawCoordinateAxis(canvas: Canvas) {
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), paint)
        canvas.drawLine(0f, height.toFloat(), 0f, 0f, paint)
    }

    override fun drawCandles(candles: List<Candle>) {
        candleList = candles
        requestLayout()
        invalidate()
    }

    override fun showNoData() {
        Toast.makeText(this.context, "Error! No data!", Toast.LENGTH_SHORT).show()
    }

//    override fun setHorizontalScrollBarEnabled(horizontalScrollBarEnabled: Boolean) {
//        super.setHorizontalScrollBarEnabled(true)
//    }

//    override fun onInterceptTouchEvent( ev: MotionEvent):Boolean{
//        return super.onInterceptTouchEvent(ev)
//    }

//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(ev: MotionEvent?): Boolean {
//        return super.onTouchEvent(ev)
//    }
}

private fun List<Candle>.max(): Double {
    if (this.isEmpty()) return -1.0
    var max = this[0].maxPrice
    for (item in this) {
        if (item.maxPrice > max) {
            max = item.maxPrice
        }
    }
    return max
}