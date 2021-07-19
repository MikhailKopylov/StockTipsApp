package ru.amk.company_list

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.amk.company_list.di.DiContainerCompanyList

class CompanyListActivity : AppCompatActivity() {


    private lateinit var diContainer:DiContainerCompanyList

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


}