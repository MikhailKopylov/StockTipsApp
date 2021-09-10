package ru.amk.company_list.list

import android.annotation.SuppressLint
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.core.company.Company
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.amk.company_list.FavoriteCompany
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

enum class SortedBy {
    NAME, SEC_ID
}

private val DEFAULT_SORTED = SortedBy.NAME

class CompanyListPresenterImpl @Inject constructor(
    private val companyListRepositoryCore: CompanyListRepository,
    private val companyListView: CompanyListView,
    private val favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore
) : CompanyListPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var companyList = listOf<FavoriteCompany>()

    private var isFavoriteCompanyUp = false

    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        val favoriteObservable = favoriteCompanyRepositoryCore.getFavoriteCompanyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val allObservable = companyListRepositoryCore.getAllCompany()
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(
            Flowable
                .combineLatest(favoriteObservable, allObservable.toFlowable(),
                { favorite, all ->
                    all.map {
                        if (favorite.contains(it)) {FavoriteCompany(it, true)}
                        else {FavoriteCompany(it, false)}
                    }.toList()
                })
                .subscribe({
                    companyList = it
                    sortBy(DEFAULT_SORTED)
                    companyListView.notifyAllDataChange(it)
            }, {
                //TODO add error handler
            })
        )
    }

    override fun getCompanyByPosition(position: Int): FavoriteCompany = companyList[position]

    override fun getCount(): Int = companyList.size

    override fun sortBy(sortedBy: SortedBy) {
        val sortedCompanyList = when (sortedBy) {
            SortedBy.NAME -> {
                companyList.sortedBy { it.company.shortName }
            }
            SortedBy.SEC_ID -> {
                companyList.sortedBy { it.company.secId }
            }
        }
        companyList = sortedCompanyList
        companyListView.notifyAllDataChange(companyList)
    }

    override fun addFavoriteCompany(company: Company) {
        favoriteCompanyRepositoryCore.addFavoriteCompany(company)
    }

    override fun deleteCompanyFromFavorite(company: Company) {
        favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(company)
    }

    override fun setFavoriteCompanyUp(isToUp: Boolean) {
        isFavoriteCompanyUp = isToUp

    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}