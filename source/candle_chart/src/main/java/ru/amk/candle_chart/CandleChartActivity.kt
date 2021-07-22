package ru.amk.candle_chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.amk.candle_chart.di.DiContainerCandle

class CandleChartActivity : AppCompatActivity() {

    private lateinit var diContainer:DiContainerCandle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_chart)

        diContainer = DiContainerCandle(this)
        diContainer.presenter.onViewCreated()
    }
}