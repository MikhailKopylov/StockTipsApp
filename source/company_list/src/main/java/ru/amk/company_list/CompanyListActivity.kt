package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import ru.amk.core.mediator.CandleChartMediator
import javax.inject.Inject

class CompanyListActivity : AppCompatActivity() {


    @Inject
    lateinit var companyListAdapter: CompanyListAdapter

    @Inject
    lateinit var companyListPresenter: CompanyListPresenter

    @Inject
    lateinit var candleChartMediator: CandleChartMediator

    companion object {
        fun startCompanyListActivity(context: Context) {
            context.startActivity(Intent(context, CompanyListActivity::class.java))
        }
    }

    @SuppressLint("CheckResult", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)

        val companyListRW: CompanyListViewImpl = findViewById(R.id.company_list_rw)

        DaggerCompanyListComponent.builder()
            .providerFacade((application as AppWithFacade).getFacade())
            .coreComponent(DaggerCoreComponent.create())
            .companyListView(companyListRW)
            .build().inject(this)

        companyListRW.layoutManager = LinearLayoutManager(this)
        companyListRW.adapter = companyListAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        companyListPresenter.onCleared()
    }
}