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
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListAdapter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.company_list.list.HeaderViewState
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject

interface RefreshDataView{
    fun refreshDone()
}
class CompanyListActivity : AppCompatActivity(), RefreshDataView {


    @Inject
    lateinit var companyListAdapter: CompanyListAdapter

    @Inject
    lateinit var companyListPresenter: CompanyListPresenter

    private val companyListRW: CompanyListViewImpl by lazy { findViewById(R.id.company_list_rw) }
    private val sortByNameTextView: TextView by lazy { findViewById(R.id.sort_by_name_text_view) }
    private val sortBySecidTextView: TextView by lazy { findViewById(R.id.sort_by_secid_text_view) }
    private val sortByPriceTextView: TextView by lazy { findViewById(R.id.sort_by_price_text_view) }
    private val favoriteToUpCheckBox: CheckBox by lazy { findViewById(R.id.favorite_to_up_switch) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private val swipeRefresh: SwipeRefreshLayout by lazy{ findViewById(R.id.swipe_container)}

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
        initRecyclerView()

        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        swipeRefresh.setOnRefreshListener {
            companyListPresenter.onViewCreated()
        }

        val settingsLoad: SharedPreferences = getPreferences(MODE_PRIVATE)
        val sortByNumber = settingsLoad.getInt(Settings.SORT_BY_KEY, 0)
        Settings.sortedBy = enumValues<SortedBy>()[sortByNumber]
        Settings.favoriteUp = settingsLoad.getBoolean(Settings.FAVORITE_UP_KEY, false)
        checkFavoriteToUp()
        sorted()
        update()
    }

    private fun initRecyclerView() {
        companyListRW.layoutManager = LinearLayoutManager(this)
        companyListRW.adapter = companyListAdapter
        companyListRW.addItemDecoration(
            DividerItemDecoration(
                companyListRW.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun daggerBuilder(companyListRW: CompanyListViewImpl) {
        DaggerCompanyListComponent.builder()
            .appProvider((application as AppWithFacade).getAppProvider())
            .coreComponent(
                DaggerCoreComponent.builder()
                    .appProvider((application as AppWithFacade).getAppProvider()).build()
            )
            .companyListView(companyListRW)
            .refreshView(this)
            .build().inject(this)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("InflateParams", "ResourceAsColor")
    private fun sorted() {
        with(Settings) {
            sortByNameTextView.setOnClickListener {
                sortedBy = SortedBy.NAME
                orderBy = when (stateSorting) {
                    SortHandler.StateSort.NAME_REVERSE_FAV_FALSE, SortHandler.StateSort.NAME_REVERSE_FAV_TRUE -> {
                        OrderBy.RIGHT
                    }
                    SortHandler.StateSort.NAME_RIGHT_FAV_FALSE, SortHandler.StateSort.NAME_RIGHT_FAV_TRUE -> {
                        OrderBy.REVERS
                    }
                    else -> {
                        OrderBy.RIGHT
                    }
                }
                update()
            }
            sortBySecidTextView.setOnClickListener {
                sortedBy = SortedBy.SEC_ID
                orderBy = when (stateSorting) {
                    SortHandler.StateSort.SEC_ID_REVERSE_FAV_FALSE, SortHandler.StateSort.SEC_ID_REVERSE_FAV_TRUE -> {
                        OrderBy.RIGHT
                    }
                    SortHandler.StateSort.SEC_ID_RIGHT_FAV_FALSE, SortHandler.StateSort.SEC_ID_RIGHT_FAV_TRUE -> {
                        OrderBy.REVERS
                    }
                    else -> {
                        OrderBy.RIGHT
                    }
                }
                update()
            }
            sortByPriceTextView.setOnClickListener {
                sortedBy = SortedBy.PRICE
                orderBy = when (stateSorting) {
                    SortHandler.StateSort.PRICE_REVERSE_FAV_FALSE, SortHandler.StateSort.PRICE_REVERSE_FAV_TRUE -> {
                        OrderBy.RIGHT
                    }
                    SortHandler.StateSort.PRICE_RIGHT_FAV_FALSE, SortHandler.StateSort.PRICE_RIGHT_FAV_TRUE -> {
                        OrderBy.REVERS
                    }
                    else -> {
                        OrderBy.RIGHT
                    }
                }
                update()
            }

        }
    }


    private fun checkFavoriteToUp() {
        if (Settings.favoriteUp) {
            findViewById<CheckBox>(R.id.favorite_to_up_switch).isChecked = true
        }
        favoriteToUpCheckBox.setOnCheckedChangeListener { _, isChecked ->
            Settings.favoriteUp = isChecked
            update()
        }
        findViewById<ImageView>(R.id.favorite_image_view).setOnClickListener {
            Settings.favoriteUp = reverseToUp()
            favoriteToUpCheckBox.isChecked = Settings.favoriteUp
            update()
        }
        findViewById<ImageView>(R.id.favorite_up_image_view).setOnClickListener {
            Settings.favoriteUp = reverseToUp()
            favoriteToUpCheckBox.isChecked = Settings.favoriteUp
            update()
        }

    }

    private fun reverseToUp(): Boolean {
        return !favoriteToUpCheckBox.isChecked
    }

    @SuppressLint("ResourceAsColor")
    private fun update() {
        companyListPresenter.sortBy(Settings.sortedBy, Settings.orderBy, Settings.favoriteUp)
        companyListRW.scrollToPosition(0)
        updateViewsHeader()
    }

    override fun onResume() {
        super.onResume()
        companyListPresenter.onViewResume()
    }

    private fun updateViewsHeader() {
        val headerViewState = HeaderViewState(this@CompanyListActivity)
        headerViewState.updateHeaderView()
        headerViewState.selectFavoriteUp()
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

    override fun refreshDone() {
        swipeRefresh.isRefreshing = false
    }

}