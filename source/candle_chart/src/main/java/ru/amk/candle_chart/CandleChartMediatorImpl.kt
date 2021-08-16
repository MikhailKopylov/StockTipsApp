package ru.amk.candle_chart

import android.content.Context
import ru.amk.core.mediator.CandleChartMediator
import javax.inject.Inject

class CandleChartMediatorImpl @Inject constructor() : CandleChartMediator {

    override fun openCandleChartScreen(context: Context, secIdCompany: String, dateTill: String) {
        CandleChartActivity.startCandleChartActivity(context, secIdCompany, dateTill)
    }
}