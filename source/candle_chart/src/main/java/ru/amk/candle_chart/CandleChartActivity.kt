package ru.amk.candle_chart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import ru.amk.candle_chart.di.DiContainerCandle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

private const val SEC_ID_COMPANY = "COMPANY_SEC_ID"
private const val DATE_TILL = "DATE_TILL"

class CandleChartActivity : AppCompatActivity() {

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

    private lateinit var diContainer: DiContainerCandle

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_chart)

        val secId = intent.getStringExtra(SEC_ID_COMPANY)
        val dateTill = intent.getStringExtra(DATE_TILL)
        diContainer = DiContainerCandle(this)
        secId?.let { diContainer.presenter.onViewCreated(secId, dateTill ?: currentDay()) }
            ?: Toast.makeText(this, "No such secId company", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        diContainer.presenter.onCleared()
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDay() = LocalDate.parse(
        SimpleDateFormat("yyyy-MM-dd").format(Date())
    ).minusDays(1L).toString()


}