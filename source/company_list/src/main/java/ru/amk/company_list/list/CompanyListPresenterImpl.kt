package ru.amk.company_list.list

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.core.company.Company
import ru.amk.core.mediator.CandleChartMediator
import javax.inject.Inject

class CompanyListPresenterImpl @Inject constructor(
    private val companyListRepositoryCore: CompanyListRepository,
    private val companyListView: CompanyListView,
    private val mediator: CandleChartMediator
) : CompanyListPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var companyList = listOf(
        Company("1", "1", "2021-12-12"),
        Company("2", "2", "2021-12-12"),
        Company("3", "3", "2021-12-12"),
        Company("4", "4", "2021-12-12"),
    )

    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        compositeDisposable.add(
            companyListRepositoryCore.getAllCompany()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        companyList = it
                        companyListView.notifyAllDataChange(it)
                    }
                }, {})
        )
    }

    override fun getCompanyByPosition(position: Int): Company = companyList[position]

    override fun getCount(): Int = companyList.size

    override fun openCandleScreen(context: Context, secId: String, dateTill: String) {
        mediator.openCandleChartScreen(context, secId, dateTill)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}