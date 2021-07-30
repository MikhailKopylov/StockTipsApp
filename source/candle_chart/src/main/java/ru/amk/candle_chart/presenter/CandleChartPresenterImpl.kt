package ru.amk.candle_chart.presenter

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.amk.candle_chart.repository.CandleRepository
import ru.amk.candle_chart.view.CandleChartView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class CandleChartPresenterImpl(
    private val candleRepository: CandleRepository,
    private val candleChartView: CandleChartView
) :
    CandleChartPresenter {

    private val compositeDisposable = CompositeDisposable()

    @RequiresApi(Build.VERSION_CODES.O)
    private val periodInDays = Period.of(0, 3, 0)


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onViewCreated(secId: String, dateTill: String) {
        val dateFrom = prevDate(dateTill)
        compositeDisposable.add(candleRepository.getCandles(secId, dateFrom, dateTill)
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
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prevDate(dateTill: String): String =
        LocalDate.parse(dateTill).minus(periodInDays).toString()

}