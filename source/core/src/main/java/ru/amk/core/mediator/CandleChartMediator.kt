package ru.amk.core.mediator

import android.content.Context

interface CandleChartMediator {

    fun openCandleChartScreen(context: Context, secIdCompany:String, dateTill:String)
}