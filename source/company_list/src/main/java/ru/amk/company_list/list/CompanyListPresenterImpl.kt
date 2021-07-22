package ru.amk.company_list.list

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.amk.company_list.repository.CompanyRepository
import ru.amk.core.company.Company

class CompanyListPresenterImpl(
    private val companyRepository: CompanyRepository,
    private val companyListView: CompanyListView
) : CompanyListPresenter {

    private var companyList = listOf(
        Company("1", "1"),
        Company("2", "2"),
        Company("3", "3"),
        Company("4", "4"),
    )

    @SuppressLint("CheckResult")
    override fun onViewCreated() {
        companyRepository.getAllCompany()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    companyList = it
                    companyListView.notifyAllDataChange(it)
                }
            }, {})
    }

    override fun getCompanyByPosition(position: Int): Company = companyList[position]

    override fun getCount(): Int = companyList.size

}