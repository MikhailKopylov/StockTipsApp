package ru.amk.company_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ru.amk.company_list.di.DaggerCompanyListComponent
import ru.amk.company_list.list.CompanyListAdapter
import ru.amk.company_list.list.CompanyListPresenter
import ru.amk.company_list.list.CompanyListViewImpl
import ru.amk.core.di.AppWithFacade
import ru.amk.core.di.DaggerCoreComponent
import javax.inject.Inject

class CompanyListActivity : AppCompatActivity() {


    @Inject
    lateinit var companyListAdapter: CompanyListAdapter

    @Inject
    lateinit var companyListPresenter: CompanyListPresenter

    private var companyListRW: CompanyListViewImpl? = null
    private var sortByNameTextView: TextView? = null
    private var sortBySecidTextView: TextView? = null
    private var nameOrderRightImageButton: ImageButton? = null
    private var secidOrderRightImageButton: ImageButton? = null
    private var nameOrderReverseImageButton: ImageButton? = null
    private var secidOrderReverseImageButton: ImageButton? = null

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

        sortByNameTextView = findViewById(R.id.sort_by_name_text_view)
        sortBySecidTextView = findViewById(R.id.sort_by_secid_text_view)
        nameOrderRightImageButton = findViewById(R.id.name_order_right_image_button)
        secidOrderRightImageButton = findViewById(R.id.secid_order_right_image_button)
        nameOrderReverseImageButton =
            findViewById(R.id.name_order_reverse_image_button)
        secidOrderReverseImageButton =
            findViewById(R.id.secid_order_reverse_image_button)

        companyListRW = findViewById(R.id.company_list_rw)

        companyListRW?.let {
            daggerBuilder(it)
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = companyListAdapter
        }
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
            sortByNameTextView?.setOnClickListener {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.RIGHT
                update()
            }
            sortBySecidTextView?.setOnClickListener {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.RIGHT
                update()
            }
            nameOrderRightImageButton?.setOnClickListener {
                if (stateSorting.ordinal in 0..3) {
                    orderBy = OrderBy.RIGHT
                    update()
                }
            }
            secidOrderRightImageButton?.setOnClickListener {
                if (stateSorting.ordinal in 4..7) {
                    orderBy = OrderBy.RIGHT
                    update()
                }
            }
            nameOrderReverseImageButton?.setOnClickListener {
                if (stateSorting.ordinal in 0..3) {
                    orderBy = OrderBy.REVERS
                    update()
                }
            }
            secidOrderReverseImageButton?.setOnClickListener {
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
        companyListRW?.scrollToPosition(0)
        with(Settings) {
            when (stateSorting) {
                SortHandler.StateSort.NAME_RIGHT_FAV_TRUE, SortHandler.StateSort.NAME_RIGHT_FAV_FALSE -> {
                    sortByNameTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.dark))
                    sortBySecidTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.gray600))
                    nameOrderRightImageButton?.setImageResource(R.drawable.arrow_down_select)
                    secidOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    nameOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                    secidOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                }

                SortHandler.StateSort.NAME_REVERSE_FAV_TRUE, SortHandler.StateSort.NAME_REVERSE_FAV_FALSE -> {
                    sortByNameTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.dark))
                    sortBySecidTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.gray600))
                    nameOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    secidOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    nameOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_select)
                    secidOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                }
                SortHandler.StateSort.SEC_ID_RIGHT_FAV_TRUE, SortHandler.StateSort.SEC_ID_RIGHT_FAV_FALSE -> {
                    sortByNameTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.gray600))
                    sortBySecidTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.dark))
                    nameOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    secidOrderRightImageButton?.setImageResource(R.drawable.arrow_down_select)
                    nameOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                    secidOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                }
                SortHandler.StateSort.SEC_ID_REVERSE_FAV_TRUE, SortHandler.StateSort.SEC_ID_REVERSE_FAV_FALSE -> {
                    sortByNameTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.gray600))
                    sortBySecidTextView?.setTextColor(ContextCompat.getColor(applicationContext,R.color.dark))
                    nameOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    secidOrderRightImageButton?.setImageResource(R.drawable.arrow_down_not_select)
                    nameOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_not_select)
                    secidOrderReverseImageButton?.setImageResource(R.drawable.arrow_up_select)
                }
            }
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