package ru.amk.candle_chart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.amk.candle_chart.di.DaggerCandleChartComponent
import ru.amk.candle.CandleChartPresenter
import ru.amk.base_view_chart.AxisYView
import ru.amk.candle.view.CandlestickViewImpl
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import ru.amk.three_line_break.ThreeLineBreakPresenter
import ru.amk.three_line_break.view.ThreeLineBreakViewImpl
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

private const val SEC_ID_COMPANY = "COMPANY_SEC_ID"
private const val DATE_TILL = "DATE_TILL"

@SuppressLint("InflateParams")
class CandleChartActivity : AppCompatActivity() {

    @Inject
    lateinit var candlestickPresenter: CandleChartPresenter

    @Inject
    lateinit var threeLineBreakPresenter: ThreeLineBreakPresenter

    private val candlestickView: CandlestickViewImpl by lazy {
        layoutInflater.inflate(R.layout.view_candlestick, null) as CandlestickViewImpl
    }
    private val threeLineBreakViewView: ThreeLineBreakViewImpl by lazy {
        layoutInflater.inflate(R.layout.view_three_line, null) as ThreeLineBreakViewImpl
    }
    private val axisYView: AxisYView by lazy { findViewById(R.id.axisYView) }
    private val scrollView: HorizontalScrollView by lazy { findViewById(R.id.candle_sv) }
    private val fab: FloatingActionButton by lazy { findViewById(R.id.change_chart_fab) }

    companion object {
        fun startCandleChartActivity(context: Context, secIdCompany: String, dateTill: String) {
            context.startActivity(
                Intent(
                    context,
                    CandleChartActivity::class.java
                ).putExtra(SEC_ID_COMPANY, secIdCompany)
                    .putExtra(DATE_TILL, dateTill)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_chart)

        val secId = intent.getStringExtra(SEC_ID_COMPANY)
        val dateTill = intent.getStringExtra(DATE_TILL)

        initView()

        initDagger()

        secId?.let {
            candlestickPresenter.onViewCreated(secId, dateTill ?: currentDay())
            threeLineBreakPresenter.onViewCreated(secId, dateTill ?: currentDay())
        } ?: Toast.makeText(this, "No such secId company", Toast.LENGTH_SHORT).show()

    }

    private fun initDagger() {
        DaggerCandleChartComponent.builder()
            .candleChartView(candlestickView)
            .threeLineBreakChartView(threeLineBreakViewView)
            .axisYView(axisYView)
            .scrollView(scrollView)
            .appProvider((application as AppWithFacade).getAppProvider())
            .coreComponent(
                DaggerCoreComponent.builder()
                    .appProvider((application as AppWithFacade).getAppProvider()).build()
            )
            .build()
            .inject(this)
    }

    private fun initView() {
        scrollView.addView(candlestickView)
        threeLineBreakViewView.visibility = GONE
        scrollView.post {
            scrollView.scrollBy(450, 0)
        }

        fab.setOnClickListener {
            if (candlestickView.isVisible()) {
                candlestickView.visibility = GONE
                threeLineBreakViewView.visibility = VISIBLE
                scrollView.removeView(candlestickView)
                scrollView.addView(threeLineBreakViewView)
                fab.setImageResource(R.drawable.icon_candlestick_chart)
            } else if (threeLineBreakViewView.isVisible()) {
                threeLineBreakViewView.visibility = GONE
                candlestickView.visibility = VISIBLE
                scrollView.removeView(threeLineBreakViewView)
                scrollView.addView(candlestickView)
                fab.setImageResource(R.drawable.icon_three_line_break_chart)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        candlestickPresenter.onCleared()
        threeLineBreakPresenter.onCleared()
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDay() = LocalDate.parse(
        SimpleDateFormat("yyyy-MM-dd").format(Date())
    ).minusDays(1L).toString()


}


