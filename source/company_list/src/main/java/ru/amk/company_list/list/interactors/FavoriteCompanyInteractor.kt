package ru.amk.company_list.list.interactors

import io.reactivex.Flowable
import ru.amk.company_list.FavoriteCompany
import ru.amk.core.company.CompanyRepositoryCore
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import javax.inject.Inject

class FavoriteCompanyInteractor  @Inject  constructor(
    private val companyListRepositoryCore: CompanyRepositoryCore,
    private val favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore
):CompanyInteractor {

    private val favoriteObservable by lazy {
        favoriteCompanyRepositoryCore.getFavoriteCompanyList()
    }
    private val allObservable by lazy {
        companyListRepositoryCore.getCompanyList(companyListRepositoryCore.getCurrentDate())
    }

    override fun getCompanies(): Flowable<List<FavoriteCompany>> =
        Flowable
            .combineLatest(favoriteObservable, allObservable.toFlowable(),
                { favorite, all ->
                    val result = mutableSetOf<FavoriteCompany>()
                    all.map {
                        val favoriteCompany = if (favorite.contains(it)) {
                            FavoriteCompany(it, true)
                        } else {
                            FavoriteCompany(it, false)
                        }
                        result.add(favoriteCompany)
                    }
                    result.toList()
                })

}