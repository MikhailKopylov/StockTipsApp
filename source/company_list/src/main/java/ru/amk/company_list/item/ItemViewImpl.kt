package ru.amk.company_list.item

import android.view.View
import android.widget.TextView
import ru.amk.candle_chart.CandleChartMediatorImpl
import ru.amk.company_list.R
import ru.amk.core.mediator.CandleChartMediator

class ItemViewImpl(private val rootView: View) : ItemView {

    private var companyNameTextView: TextView = rootView.findViewById(R.id.company_name_textview)
    private var secIdTextView: TextView = rootView.findViewById(R.id.sec_id_textview)

    override fun setCompanyName(name: String) {
        companyNameTextView.text = name
    }

    override fun setCompanySetId(secId: String) {
        secIdTextView.text = secId
    }

    override fun openCandleScreen(secId: String, dateTill: String) {
        val candleChartMediator: CandleChartMediator = CandleChartMediatorImpl()
        candleChartMediator.openCandleChartScreen(rootView.context, secId, dateTill)
    }
}