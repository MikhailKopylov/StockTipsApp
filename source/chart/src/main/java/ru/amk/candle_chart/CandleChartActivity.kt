package ru.amk.candle_chart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.amk.candle_chart.di.DaggerChartComponent
import ru.amk.candle.CandleChartPresenter
import ru.amk.base_view_chart.AxisYView
import ru.amk.candle.view.CandlestickViewImpl
import ru.amk.candle_chart.presenter.ChartPresenter
import ru.amk.core.candle.ColorCandle
import ru.amk.core.company.Company
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import ru.amk.three_line_break.ThreeLineBreakPresenter
import ru.amk.three_line_break.view.ThreeLineBreakViewImpl
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

private const val COMPANY = "Company"
private const val FAVORITE = "Favorite"

interface ChartView {
    fun setPrice(price: String)
    fun setChangePrice(changePrice:String, color:ColorCandle)
}

@SuppressLint("InflateParams")
class CandleChartActivity : AppCompatActivity(), ChartView {

    @Inject
    lateinit var candlestickPresenter: CandleChartPresenter

    @Inject
    lateinit var threeLineBreakPresenter: ThreeLineBreakPresenter

    @Inject
    lateinit var chartPresenter: ChartPresenter

    private var isFavorite = false
    private val candlestickView: CandlestickViewImpl by lazy {
        layoutInflater.inflate(R.layout.view_candlestick, null) as CandlestickViewImpl
    }
    private val threeLineBreakViewView: ThreeLineBreakViewImpl by lazy {
        layoutInflater.inflate(R.layout.view_three_line, null) as ThreeLineBreakViewImpl
    }
    private val axisYView: AxisYView by lazy { findViewById(R.id.axisYView) }
    private val scrollView: HorizontalScrollView by lazy { findViewById(R.id.candle_sv) }
    private val fab: FloatingActionButton by lazy { findViewById(R.id.change_chart_fab) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.chart_toolbar) }
    private val lastPriceTextView: TextView by lazy { findViewById(R.id.last_price_textview) }
    private val changePriceTextView: TextView by lazy { findViewById(R.id.change_price_textview) }
    private val company: Company by lazy {
        return@lazy intent.getParcelableExtra<Company>(COMPANY) as Company
    }

    companion object {
        fun startCandleChartActivity(context: Context, company: Company, isFavorite: Boolean) {
            context.startActivity(
                    Intent(context, CandleChartActivity::class.java)
                            .putExtra(COMPANY, company as Parcelable)
                            .putExtra(FAVORITE, isFavorite)
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candle_chart)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        isFavorite = intent.getBooleanExtra(FAVORITE, false)
        title = company.shortName
        val secId = company.secId
        val dateTill = company.date

        initDagger()
        initView()


        candlestickPresenter.onViewCreated(secId, dateTill)
        threeLineBreakPresenter.onViewCreated(secId, dateTill)

    }

    private fun initDagger() {
        DaggerChartComponent.builder()
                .chartView(this)
                .candleChartView(candlestickView)
                .threeLineBreakChartView(threeLineBreakViewView)
                .axisYView(axisYView)
                .scrollView(scrollView)
                .appProvider((application as AppWithFacade).getAppProvider())
                .coreComponent(
                        DaggerCoreComponent.builder()
                                .appProvider((application as AppWithFacade).getAppProvider()).build()
                )
                .build()
                .inject(this)
    }

    private fun initView() {
        scrollView.addView(candlestickView)
        threeLineBreakViewView.visibility = GONE
        scrollView.post {
            scrollView.scrollBy(450, 0)
        }

        chartPresenter.setLastPrice(company)
        chartPresenter.setChangePrice(company)
        fab.setOnClickListener {
            if (candlestickView.isVisible()) {
                candlestickView.visibility = GONE
                threeLineBreakViewView.visibility = VISIBLE
                scrollView.removeView(candlestickView)
                scrollView.addView(threeLineBreakViewView)
                fab.setImageResource(R.drawable.icon_candlestick_chart)
            } else if (threeLineBreakViewView.isVisible()) {
                threeLineBreakViewView.visibility = GONE
                candlestickView.visibility = VISIBLE
                scrollView.removeView(threeLineBreakViewView)
                scrollView.addView(candlestickView)
                fab.setImageResource(R.drawable.icon_three_line_break_chart)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        candlestickPresenter.onCleared()
        threeLineBreakPresenter.onCleared()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menu?.let {
            val favorite: MenuItem = it.findItem(R.id.favorite_item)
            if (isFavorite) {
                favorite.setIcon(R.drawable.favorite)
            } else {
                favorite.setIcon(R.drawable.non_favorite)
            }
        }
        return true
    }

    fun favoriteClick(item: MenuItem) {
        if (isFavorite) {
            isFavorite = false
            item.setIcon(R.drawable.non_favorite)
        } else {
            isFavorite = true
            item.setIcon(R.drawable.favorite)
        }
        chartPresenter.changeFavoriteStatus(isFavorite, company)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDay() = LocalDate.parse(
            SimpleDateFormat("yyyy-MM-dd").format(Date())
    ).minusDays(1L).toString()

    override fun setPrice(price: String) {
        lastPriceTextView.text = price
    }

    override fun setChangePrice(changePrice: String, color:ColorCandle) {
        changePriceTextView.text = changePrice
        changePriceTextView.setTextColor(color.paint.color)
    }


}


