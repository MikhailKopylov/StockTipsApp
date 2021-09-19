package ru.amk.three_line_break.view

import ru.amk.core.three_line_break.ThreeLineBreak
import ru.amk.three_line_break.ThreeLineBreakPresenter

interface ThreeLineBreakView {
    fun drawThreeLine(threeLineBreaks: List<ThreeLineBreak>)
    fun setThreeLinePresenter(threeLineBreakPresenter: ThreeLineBreakPresenter)
    fun getWidthView(): Int
    fun isVisible():Boolean
    fun showNoData()
}