package ru.amk.candle_chart.presenter

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.view.CandleChartView

class CandleChartPresenterImpl(
    private val candleRepository: CandleRepository,
    private val candleChartView: CandleChartView
) :
    CandleChartPresenter {


    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        candleRepository.getCandles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {
                    candleChartView.drawCandles(it)
                } else {
                    candleChartView.showNoData()
                }
            }, {
                candleChartView.showNoData()
            })
    }
}