package ru.amk.core.mediator

import android.content.Context
import ru.amk.core.company.Company

interface CandleChartMediator {

    fun openCandleChartScreen(
        context: Context,
        company: Company,
        isFavorite: Boolean
    )
}