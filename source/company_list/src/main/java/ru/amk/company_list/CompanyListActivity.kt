package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.amk.company_list.di.DiContainerCompanyList

class CompanyListActivity : AppCompatActivity() {

    companion object {
        fun startCompanyListActivity(context: Context) {
            context.startActivity(Intent(context, CompanyListActivity::class.java))
        }
    }

    private lateinit var diContainer: DiContainerCompanyList

    @SuppressLint("CheckResult", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)

        diContainer = DiContainerCompanyList(this)

        val companyListRW = diContainer.companyListRW
        companyListRW.layoutManager = LinearLayoutManager(this)

        val adapter = diContainer.companyListAdapter
        companyListRW.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        diContainer.companyListPresenter.onCleared()
    }
}