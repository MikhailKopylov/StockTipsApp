package ru.amk.candle_chart.presenter

interface CandleChartPresenter {

    fun onViewCreated(secId:String, dateTill:String)
    fun onCleared()
}