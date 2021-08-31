package ru.amk.company_list.list

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.core.company.Company
import javax.inject.Inject

enum class SortedBy {
    NAME, SEC_ID
}

private val DEFAULT_SORTED = SortedBy.NAME

class CompanyListPresenterImpl @Inject constructor(
    private val companyListRepositoryCore: CompanyListRepository,
    private val companyListView: CompanyListView
) : CompanyListPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var companyList = listOf<Company>()

    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        compositeDisposable.add(
            companyListRepositoryCore.getAllCompany()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        companyList = it
                        sortBy(DEFAULT_SORTED)
                        companyListView.notifyAllDataChange(it)
                    }
                }, {
                    //TODO add error handler
                })
        )
    }

    override fun getCompanyByPosition(position: Int): Company = companyList[position]

    override fun getCount(): Int = companyList.size

    override fun sortBy(sortedBy: SortedBy) {
        val sortedCompanyList = when (sortedBy) {
            SortedBy.NAME -> {
                companyList.sortedBy { it.shortName }
            }
            SortedBy.SEC_ID -> {
                companyList.sortedBy { it.secId }
            }
        }
        companyList = sortedCompanyList
        companyListView.notifyAllDataChange(companyList)
    }


    override fun onCleared() {
        compositeDisposable.clear()
    }

}