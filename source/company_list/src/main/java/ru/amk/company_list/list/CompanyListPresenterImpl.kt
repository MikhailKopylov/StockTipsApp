package ru.amk.company_list.list

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.amk.company_list.repository.CompanyListRepository
import ru.amk.core.company.Company
import javax.inject.Inject

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
                        companyListView.notifyAllDataChange(it)
                    }
                }, {
                    //TODO add error handler
                })
        )
    }

    override fun getCompanyByPosition(position: Int): Company = companyList[position]

    override fun getCount(): Int = companyList.size
    override fun onCleared() {
        compositeDisposable.clear()
    }

}