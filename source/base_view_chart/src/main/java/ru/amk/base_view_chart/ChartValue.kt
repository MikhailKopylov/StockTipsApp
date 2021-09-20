package ru.amk.base_view_chart

object ChartValue {
    const val OFFSET_AXIS_Y = 50f
    const val COUNT_OF_VALUE_Y_AXIS = 7
    const val SEGMENT_LENGTH = 5f

    var coordZeroX = 0f// начало оси X
    var coordEndXAxis = 0f//Конец оси X
    var stepYAxis = 0f//Расстояние между сигнатурами оси Y
    var maxValueYAxis = 0.0//максимально значение по оси Y
    var stepValueYAxis = maxValueYAxis / COUNT_OF_VALUE_Y_AXIS//шаг значение по оси
    var heightView = 0
    val widthPerView: Int = 50
    var coordZeroY = 1f// начало оси Y
    var coordEndYAxis = 0f//Конец оси Yinternal
    var heightPerValue: Double = 0.0
    var currentX: Int = 0
    var widthSize = 0
    var stepXAxis = 0f//Расстояние между подписями оси X
    var minValueYAxis:Double = 0.0

}