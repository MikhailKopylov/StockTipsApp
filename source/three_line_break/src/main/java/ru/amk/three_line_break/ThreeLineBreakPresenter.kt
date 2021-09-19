package ru.amk.three_line_break

interface ThreeLineBreakPresenter {

    fun onViewCreated(secId: String, dateTill: String)
    fun scrollToLeft()
    fun onCleared()
}