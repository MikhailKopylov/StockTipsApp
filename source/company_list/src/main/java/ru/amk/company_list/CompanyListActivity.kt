package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListAdapter
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

        daggerBuilder(companyListRW)

        companyListRW.layoutManager = LinearLayoutManager(this)
        companyListRW.adapter = companyListAdapter

        sorted()

        val settingsLoad: SharedPreferences = getPreferences(MODE_PRIVATE)
        val sortByNumber = settingsLoad.getInt(Settings.SORT_BY_KEY, 0)
        Settings.sortedBy = enumValues<SortedBy>()[sortByNumber]
        Settings.favoriteUp = settingsLoad.getBoolean(Settings.FAVORITE_UP_KEY, false)
        checkFavoriteToUp()

    }

    private fun daggerBuilder(companyListRW: CompanyListViewImpl) {
        DaggerCompanyListComponent.builder()
            .appProvider((application as AppWithFacade).getAppProvider())
            .coreComponent(
                DaggerCoreComponent.builder()
                    .appProvider((application as AppWithFacade).getAppProvider()).build()
            )
            .companyListView(companyListRW)
            .build().inject(this)
    }


    @SuppressLint("InflateParams")
    private fun sorted() {
        findViewById<Button>(R.id.sort_button).setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheet = layoutInflater.inflate(R.layout.sorting_bottom_sheet, null)

            bottomSheet.findViewById<ChipGroup>(R.id.selection_sorted_chip_group)
                .setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.sort_by_name_chip -> Settings.sortedBy = SortedBy.NAME
                        R.id.sort_by_secid_chip -> Settings.sortedBy = SortedBy.SEC_ID
                    }
                    companyListPresenter.sortBy(Settings.sortedBy, Settings.favoriteUp)
                    dialog.dismiss()
                }
            dialog.setContentView(bottomSheet)
            dialog.show()
        }
    }


    private fun checkFavoriteToUp() {
        if(Settings.favoriteUp){
            findViewById<Chip>(R.id.favorite_to_up_chip).isChecked = true
        }
        findViewById<Chip>(R.id.favorite_to_up_chip).setOnCheckedChangeListener { _, isChecked ->
            Settings.favoriteUp = isChecked
            companyListPresenter.sortBy(Settings.sortedBy, Settings.favoriteUp)
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStop() {
        super.onStop()
        val sharedPref: SharedPreferences = getPreferences(MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(Settings.SORT_BY_KEY, Settings.sortedBy.ordinal)
        editor.putBoolean(Settings.FAVORITE_UP_KEY, Settings.favoriteUp)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        companyListPresenter.onCleared()

    }

}