package ru.amk.company_list.list

import android.annotation.SuppressLint
import android.content.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.core.company.Company
import io.reactivex.schedulers.Schedulers
import ru.amk.company_list.*
import ru.amk.company_list.list.handlers.FilterHandler
import ru.amk.company_list.list.handlers.SortHandler
import ru.amk.company_list.list.handlers.SortedBy
import ru.amk.company_list.list.interactors.CompanyInteractor
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import javax.inject.Inject
import javax.inject.Named


class CompanyListPresenterImpl @Inject constructor(
    @Named("filter") private val filterInteractor: CompanyInteractor,
    private val companyListView: CompanyListView,
    private val favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore,
    private val activityView: ActivityView,
) : CompanyListPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var companyList = listOf<FavoriteCompany>()
    private var sourceCompanyList = companyList

    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        compositeDisposable.add(
            filterInteractor.getCompanies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    companyList = it
                    sourceCompanyList = it
                    update()
                }, {
                    //TODO add error handler
                })
        )
    }


    override fun getCompanyByPosition(position: Int): FavoriteCompany = companyList[position]

    override fun getCount(): Int =
        companyList.size

    private fun update() {
        activityView.updateViewsHeader()
        companyListView.notifyAllDataChange(companyList)
        activityView.refreshDone()
    }

    override fun clickSorting(sorted: SortedBy) {
        SortHandler.setSortedBy(sorted)
    }

    override fun clickFavoriteUp(isFavoriteUp: Boolean) {
        SortHandler.setFavoriteUp(isFavoriteUp)
    }

    override fun addFavoriteCompany(company: Company) {
        favoriteCompanyRepositoryCore.addFavoriteCompany(company)
    }

    override fun deleteCompanyFromFavorite(company: Company) {
        favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(company)
    }

    override fun filterCompany(filterName: String) {
        FilterHandler.filterCompany(filterName)
    }

    override fun viewOnStop() {
        val sharedPref: SharedPreferences = activityView.getPreference()
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(SortHandler.STATE_SORT, SortHandler.stateSorting.ordinal)
        editor.apply()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
