package ru.amk.company_list.item

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import ru.amk.candle_chart.CandleChartMediatorImpl
import ru.amk.company_list.R
import ru.amk.core.candle.ColorCandle
import ru.amk.core.company.Company
import ru.amk.core.mediator.CandleChartMediator
import javax.inject.Inject

class ItemViewImpl @Inject constructor(private val rootView: View) : ItemView {

    private val companyNameTextView: TextView by lazy{rootView.findViewById(R.id.company_name_textview)}
    private val secIdTextView: TextView by lazy{rootView.findViewById(R.id.sec_id_textview)}
    private val changePriceTextView: TextView by lazy{rootView.findViewById(R.id.change_price_textView)}
    private val changePercentTextView: TextView by lazy{rootView.findViewById(R.id.change_percent_textView)}
    private val favoriteButton: ImageButton by lazy{rootView.findViewById(R.id.favorite_button)}

    override fun setCompanyName(name: String) {
        companyNameTextView.text = name
    }

    override fun setCompanySetId(secId: String) {
        secIdTextView.text = secId
    }

    override fun openCandleScreen(
        company:Company,
        isFavorite: Boolean
    ) {
        val candleChartMediator: CandleChartMediator = CandleChartMediatorImpl()
        candleChartMediator.openCandleChartScreen(
            rootView.context,
            company,
            isFavorite
        )
    }

    override fun setFavorite(favorite: Boolean) {
        if (favorite) {
            favoriteButton.setBackgroundResource(R.drawable.favorite)
        } else {
            favoriteButton.setBackgroundResource(R.drawable.non_favorite)
        }
    }

    override fun setPriceChange(changePrice: String, colorText: ColorCandle) {
        changePriceTextView.text = changePrice
        changePriceTextView.setTextColor(colorText.paint.color)
    }

    override fun setPercentChange(changePercent: String, colorText:ColorCandle) {
        changePercentTextView.text = changePercent
        changePercentTextView.setTextColor(colorText.paint.color)
    }
}