package ru.amk.candle_chart

import android.content.Context
import ru.amk.core.mediator.CandleChartMediator

class CandleChartMediatorImpl : CandleChartMediator {
    override fun openCandleChartScreen(context: Context, secIdCompany: String, dateTill:String) {
        CandleChartActivity.startCandleChartActivity(context, secIdCompany, dateTill)
    }
}