package ru.amk.candle_chart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import ru.amk.candle_chart.di.DaggerCandleChartComponent
import ru.amk.candle_chart.presenter.CandleChartPresenter
import ru.amk.candle_chart.view.AxisYView
import ru.amk.candle_chart.view.CandleChartView
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

private const val SEC_ID_COMPANY = "COMPANY_SEC_ID"
private const val DATE_TILL = "DATE_TILL"

class CandleChartActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: CandleChartPresenter

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

        val candleChartView: CandleChartView = findViewById(R.id.candle_chart)
        val axisYView: AxisYView = findViewById(R.id.axisYView)
        val scrollView: HorizontalScrollView = findViewById(R.id.candle_sv)

        scrollView.post {
            scrollView.scrollBy(450, 0)
        }
        DaggerCandleChartComponent.builder()
            .candleChartView(candleChartView)
            .axisYView(axisYView)
            .scrollView(scrollView)
            .appProvider((application as AppWithFacade).getAppProvider())
            .coreComponent(DaggerCoreComponent.builder().appProvider((application as AppWithFacade).getAppProvider()).build())
            .build()
            .inject(this)

        secId?.let { presenter.onViewCreated(secId, dateTill ?: currentDay()) }
            ?: Toast.makeText(this, "No such secId company", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onCleared()
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDay() = LocalDate.parse(
        SimpleDateFormat("yyyy-MM-dd").format(Date())
    ).minusDays(1L).toString()


}