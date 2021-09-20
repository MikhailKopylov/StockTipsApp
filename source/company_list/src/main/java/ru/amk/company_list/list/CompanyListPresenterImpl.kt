package ru.amk.company_list.list

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.core.company.Company
import io.reactivex.schedulers.Schedulers
import ru.amk.company_list.*
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import javax.inject.Inject


class CompanyListPresenterImpl @Inject constructor(
    private val companyListRepositoryCore: CompanyListRepository,
    private val companyListView: CompanyListView,
    private val favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore
) : CompanyListPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var companyList = listOf<FavoriteCompany>()
    private var notFilterCompanyList = companyList
    private var filterCompanyName = ""
    private val favoriteObservable by lazy {
        favoriteCompanyRepositoryCore.getFavoriteCompanyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    private val allObservable by lazy {
        companyListRepositoryCore.getAllCompany()
            .observeOn(AndroidSchedulers.mainThread())
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated() {

        compositeDisposable.add(
            Flowable
                .combineLatest(favoriteObservable, allObservable.toFlowable(),
                    { favorite, all ->
                        all.map {
                            if (favorite.contains(it)) {
                                FavoriteCompany(it, true)
                            } else {
                                FavoriteCompany(it, false)
                            }
                        }.toList()
                    })
                .subscribe({
                    companyList = it
                    notFilterCompanyList = it
                    filterCompany(filterCompanyName)
                    sortBy(Settings.sortedBy, Settings.orderBy, Settings.favoriteUp)
                    companyListView.notifyAllDataChange(companyList)
                }, {
                    //TODO add error handler
                })
        )
    }

    override fun getCompanyByPosition(position: Int): FavoriteCompany = companyList[position]

    override fun getCount(): Int = companyList.size

    override fun sortBy(sortedBy: SortedBy, orderBy: OrderBy, favoriteUp: Boolean) {

        Settings.favoriteUp = favoriteUp
        Settings.sortedBy = sortedBy
        val sortedCompanyList: List<FavoriteCompany> = SortHandler(companyList)
            .sort(sortedBy, orderBy, favoriteUp)

        val oldCompanyList = companyList
        companyList = sortedCompanyList
        companyListView.notifyDiffDataChange(oldCompanyList, companyList)
    }


    override fun addFavoriteCompany(company: Company) {
        favoriteCompanyRepositoryCore.addFavoriteCompany(company)
    }

    override fun deleteCompanyFromFavorite(company: Company) {
        favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(company)
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }

    override fun filterCompany(filterName: String) {
        filterCompanyName = filterName
        companyList = if (filterCompanyName.isEmpty()) {
            notFilterCompanyList
        } else {
            val filterCompanyList = notFilterCompanyList.filter {
                it.company.secId.contains(filterName, true) ||
                        it.company.shortName.contains(filterName, true)
            }
                .toList()
            filterCompanyList
        }
        with(Settings) {
            val sortedCompanyList: List<FavoriteCompany> = SortHandler(companyList)
                .sort(sortedBy, orderBy, favoriteUp)
            companyList = sortedCompanyList
        }
        companyListView.notifyAllDataChange(companyList)
    }

    override fun onViewResume() {
        filterCompany(filterCompanyName)
        sortBy(Settings.sortedBy, Settings.orderBy, Settings.favoriteUp)
        companyListView.notifyAllDataChange(companyList)
        compositeDisposable.add(favoriteObservable.map { companySet ->
            notFilterCompanyList.map {
                if (companySet.contains(it.company)) {
                    FavoriteCompany(it.company, true)
                } else {
                    FavoriteCompany(it.company, false)
                }
            }.toList()
        }
            .subscribe({
                companyList = it
                notFilterCompanyList = it
                filterCompany(filterCompanyName)
                sortBy(Settings.sortedBy, Settings.orderBy, Settings.favoriteUp)
                companyListView.notifyAllDataChange(companyList)
            }, {
                //TODO add error handler
            })
        )

    }

}