package ru.amk.candle_chart.presenter

import android.annotation.SuppressLint
import android.os.Build
import android.widget.HorizontalScrollView
import androidx.annotation.RequiresApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.amk.core.three_line_break.ThreeLineRepository
import ru.amk.three_line_break.ThreeLineBreakPresenter
import ru.amk.three_line_break.view.ThreeLineBreakView
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

class ThreeLineBreakPresenterImpl @Inject constructor(
    private val threeLineRepository: ThreeLineRepository,
    private val threeLineBreakView: ThreeLineBreakView,
    private val scrollView: HorizontalScrollView
) : ThreeLineBreakPresenter {

    private val compositeDisposable = CompositeDisposable()

    @RequiresApi(Build.VERSION_CODES.O)
    private val periodInDays = Period.of(0, 3, 0)


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onViewCreated(secId: String, dateTill: String) {
        val dateFrom = prevDate(dateTill)
        compositeDisposable.add(
            threeLineRepository.getTreeLineList(secId, dateFrom, dateTill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        threeLineBreakView.drawThreeLine(it)
                    } else {
                        threeLineBreakView.showNoData()
                    }
                }, {
                    threeLineBreakView.showNoData()
                })
        )
        threeLineBreakView.setThreeLinePresenter(this)
    }

    override fun scrollToLeft() {
        scrollView.post { scrollView.scrollBy(threeLineBreakView.getWidthView(), 0) }
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prevDate(dateTill: String): String =
        LocalDate.parse(dateTill).minus(periodInDays).toString()


}