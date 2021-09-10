package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.list.SortedBy
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject

class CompanyListActivity : AppCompatActivity() {


    @Inject
    lateinit var companyListAdapter: CompanyListAdapter

    @Inject
    lateinit var companyListPresenter: CompanyListPresenter

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
            .appProvider((application as AppWithFacade).getAppProvider())
            .coreComponent(
                DaggerCoreComponent.builder()
                    .appProvider((application as AppWithFacade).getAppProvider()).build()
            )
            .companyListView(companyListRW)
            .build().inject(this)

        companyListRW.layoutManager = LinearLayoutManager(this)
        companyListRW.adapter = companyListAdapter

        sorted()

        checkFavoriteToUp()
    }


    @SuppressLint("InflateParams")
    private fun sorted() {
        findViewById<Button>(R.id.sort_button).setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheet = layoutInflater.inflate(R.layout.sorting_bottom_sheet, null)
            bottomSheet.findViewById<ChipGroup>(R.id.selection_sorted_chip_group)
                .setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.sort_by_name_chip -> {
                            companyListPresenter.sortBy(SortedBy.NAME)
                            dialog.dismiss()
                        }
                        R.id.sort_by_secid_chip -> {
                            companyListPresenter.sortBy(SortedBy.SEC_ID)
                            dialog.dismiss()
                        }
                    }
                }
            dialog.setContentView(bottomSheet)
            dialog.show()
        }
    }

    private fun checkFavoriteToUp() {
        findViewById<Chip>(R.id.favorite_to_up_chip).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                companyListPresenter.setFavoriteCompanyUp(true)
            } else {
                companyListPresenter.setFavoriteCompanyUp(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        companyListPresenter.onCleared()
    }
}