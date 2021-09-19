package ru.amk.candle

interface CandleChartPresenter {

    fun onViewCreated(secId: String, dateTill: String)
    fun scrollToLeft()
    fun onCleared()
}