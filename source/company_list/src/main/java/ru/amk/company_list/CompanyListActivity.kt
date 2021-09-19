package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListAdapter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.repository.HeaderViewState
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject

class CompanyListActivity : AppCompatActivity() {


    @Inject
    lateinit var companyListAdapter: CompanyListAdapter

    @Inject
    lateinit var companyListPresenter: CompanyListPresenter

    private val companyListRW: CompanyListViewImpl by lazy { findViewById(R.id.company_list_rw) }
    private val sortByNameTextView: TextView by lazy { findViewById(R.id.sort_by_name_text_view) }
    private val sortBySecidTextView: TextView by lazy { findViewById(R.id.sort_by_secid_text_view) }
    private val nameOrderRightImageButton: ImageButton by lazy { findViewById(R.id.name_order_right_image_button) }
    private val secidOrderRightImageButton: ImageButton by lazy { findViewById(R.id.secid_order_right_image_button) }
    private val nameOrderReverseImageButton: ImageButton by lazy { findViewById(R.id.name_order_reverse_image_button) }
    private val secidOrderReverseImageButton: ImageButton by lazy { findViewById(R.id.secid_order_reverse_image_button) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }

    companion object {
        fun startCompanyListActivity(context: Context) {
            context.startActivity(Intent(context, CompanyListActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CheckResult", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)
        setSupportActionBar(toolbar)
        title = "График акций"

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


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("InflateParams", "ResourceAsColor")
    private fun sorted() {
        with(Settings) {
            sortByNameTextView.setOnClickListener {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.RIGHT
                update()
            }
            sortBySecidTextView.setOnClickListener {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.RIGHT
                update()
            }
            nameOrderRightImageButton.setOnClickListener {
                if (stateSorting.ordinal in 0..3) {
                    orderBy = OrderBy.RIGHT
                    update()
                }
            }
            secidOrderRightImageButton.setOnClickListener {
                if (stateSorting.ordinal in 4..7) {
                    orderBy = OrderBy.RIGHT
                    update()
                }
            }
            nameOrderReverseImageButton.setOnClickListener {
                if (stateSorting.ordinal in 0..3) {
                    orderBy = OrderBy.REVERS
                    update()
                }
            }
            secidOrderReverseImageButton.setOnClickListener {
                if (stateSorting.ordinal in 4..7) {
                    orderBy = OrderBy.REVERS
                    update()
                }
            }
        }
    }


    private fun checkFavoriteToUp() {
        if (Settings.favoriteUp) {
            findViewById<CheckBox>(R.id.favorite_to_up_switch).isChecked = true
        }
        findViewById<CheckBox>(R.id.favorite_to_up_switch).setOnCheckedChangeListener { _, isChecked ->
            Settings.favoriteUp = isChecked
            update()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun update() {
        companyListPresenter.sortBy(Settings.sortedBy, Settings.orderBy, Settings.favoriteUp)
        companyListRW.scrollToPosition(0)
        with(Settings) {
            updateViewsHeader()
        }
    }

    private fun Settings.updateViewsHeader() {
        val headerViewState = HeaderViewState(this@CompanyListActivity)
        when (stateSorting) {
            SortHandler.StateSort.NAME_RIGHT_FAV_TRUE,
            SortHandler.StateSort.NAME_RIGHT_FAV_FALSE -> {
                headerViewState.nameRight()
            }
            SortHandler.StateSort.NAME_REVERSE_FAV_TRUE,
            SortHandler.StateSort.NAME_REVERSE_FAV_FALSE -> {
                headerViewState.nameRevers()
            }
            SortHandler.StateSort.SEC_ID_RIGHT_FAV_TRUE,
            SortHandler.StateSort.SEC_ID_RIGHT_FAV_FALSE -> {
                headerViewState.secIdRight()
            }
            SortHandler.StateSort.SEC_ID_REVERSE_FAV_TRUE,
            SortHandler.StateSort.SEC_ID_REVERSE_FAV_FALSE -> {
                headerViewState.secIdReverse()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.let {
            val search: MenuItem = it.findItem(R.id.action_search)
            val searchView = search.actionView as SearchView
            searchView.queryHint = "Название или тикер"
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { companyListPresenter.filterCompany(query) }
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { companyListPresenter.filterCompany(newText) }
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            Toast.makeText(this, R.string.search_hint, Toast.LENGTH_SHORT).show()
        }
        return true
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