package ru.amk.company_list.list.interactors

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.list.handlers.FilterHandler
import javax.inject.Inject
import javax.inject.Named

class FilterCompanyInteractor @Inject constructor(
    @Named("sort") private val sortCompanyInteractor: CompanyInteractor,
) : CompanyInteractor {

    override fun getCompanies(): Flowable<List<FavoriteCompany>> {
        val filterFlowable = FilterHandler.filterObserve.switchMap {
            Observable.just(it)
        }.toFlowable(BackpressureStrategy.LATEST)
        return Flowable.combineLatest(
            sortCompanyInteractor.getCompanies(),
            filterFlowable
        ) { companyList, filterQuery ->
            if (filterQuery.isEmpty()) {
                companyList
            } else {
                companyList.filter {
                    it.company.shortName.contains(filterQuery, true) ||
                            it.company.secId.contains(filterQuery, true)
                }
            }
        }
    }
}