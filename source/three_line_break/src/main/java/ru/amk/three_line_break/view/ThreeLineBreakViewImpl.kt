package ru.amk.three_line_break.view

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import ru.amk.base_view_chart.*
import ru.amk.base_view_chart.ChartValue.currentX
import ru.amk.base_view_chart.ChartValue.heightPerValue
import ru.amk.base_view_chart.ChartValue.coordZeroY
import ru.amk.base_view_chart.ChartValue.minValueYAxis
import ru.amk.base_view_chart.ChartValue.widthPerView
import ru.amk.core.three_line_break.ThreeLineBreak
import ru.amk.three_line_break.ThreeLineBreakPresenter

class ThreeLineBreakViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ThreeLineBreakView, BaseView(context, attrs, defStyleAttr) {

    private var threeLineBreaks: List<ThreeLineBreak> = emptyList()
    override fun drawThreeLine(threeLineBreaks: List<ThreeLineBreak>) {
        this.threeLineBreaks = threeLineBreaks
        requestLayout()
        invalidate()
    }

    private lateinit var _treeLinePresenter: ThreeLineBreakPresenter
    override fun setThreeLinePresenter(threeLineBreakPresenter: ThreeLineBreakPresenter) {
        _treeLinePresenter = threeLineBreakPresenter

    }

    override fun getWidthView(): Int = widthPerView * threeLineBreaks.size


    override fun isVisible(): Boolean = isVisible

    override fun showNoData() {
        Toast.makeText(this.context, "Error! No data!", Toast.LENGTH_SHORT).show()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        _treeLinePresenter.scrollToLeft()
        when (widthMode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED -> {

                ChartValue.stepXAxis = widthPerView.toFloat()
                if (threeLineBreaks.isNotEmpty()) {
                    heightPerValue = coordZeroY / ChartValue.maxValueYAxis
                }
                setMeasuredDimension(widthPerView * threeLineBreaks.size, ChartValue.heightView)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas?) {
        if (threeLineBreaks.isNotEmpty()) {
            currentX = 0
//            heightPerValue = coordZeroY / threeLineBreaks.diffThreeLine()

            canvas?.let { onDrawCoordinateGrid(canvas) }
            for ((index, item) in threeLineBreaks.withIndex()) {
                canvas?.let {
                    onDrawXAxisSignatures(canvas, item.date, index)
                    onDrawBody(canvas, item)
                }
                currentX += widthPerView
            }
        }
    }

    private fun onDrawBody(canvas: Canvas, threeLineBreak: ThreeLineBreak) {
        val left = (currentX + widthPerView / 2 - widthPerView / 2 + 1).toFloat()
        val top =
            coordZeroY - (heightPerValue * (threeLineBreak.maxPrice - minValueYAxis)).toFloat()
        val right = (currentX + widthPerView / 2 + widthPerView / 2 - 1).toFloat()
        val bottom =
            coordZeroY - (heightPerValue * (threeLineBreak.minPrice - minValueYAxis)).toFloat()
        if (threeLineBreak.maxPrice == threeLineBreak.minPrice) {
            canvas.drawLine(left, top, right, bottom, threeLineBreak.colorBody.paint)
        } else {
            canvas.drawRect(left, top, right, bottom, threeLineBreak.colorBody.paint)
        }
    }


}