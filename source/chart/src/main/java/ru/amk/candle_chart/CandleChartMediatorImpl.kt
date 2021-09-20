package ru.amk.candle_chart

import android.content.Context
import ru.amk.core.company.Company
import ru.amk.core.mediator.CandleChartMediator

class CandleChartMediatorImpl : CandleChartMediator {
    override fun openCandleChartScreen(
        context: Context,
        company: Company,
        isFavorite: Boolean
    ) {
        CandleChartActivity.startCandleChartActivity(
            context,
            company,
            isFavorite
        )
    }
}