package ru.amk.company_list.list.interactors

import io.reactivex.Flowable
import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.list.handlers.SortHandler
import ru.amk.company_list.list.handlers.StateSort
import javax.inject.Inject
import javax.inject.Named

class SortCompanyInteractor @Inject constructor(
    @Named("favorite") private val companyInteractor: CompanyInteractor,
) : CompanyInteractor {

    override fun getCompanies(): Flowable<List<FavoriteCompany>> =
        Flowable.combineLatest(
            companyInteractor.getCompanies(),
            SortHandler.getStateSorting(),
            { companyList, stateSorting ->
                sort(companyList, stateSorting)
            })

    private fun sort(companies: List<FavoriteCompany>, state: StateSort): List<FavoriteCompany> =
        when (state) {
            StateSort.NAME_ASC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }.sortedBy { it.company.shortName }
                        .toMutableList()
                val withOutFavoriteList =
                    companies.filter { !it.isFavorite }.sortedBy { it.company.shortName }
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.SEC_ID_ASC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }.sortedBy { it.company.secId }
                        .toMutableList()
                val withOutFavoriteList =
                    companies.filter { !it.isFavorite }.sortedBy { it.company.secId }
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.NAME_DESC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }.sortedBy { it.company.shortName }
                        .reversed()
                        .toMutableList()
                val withOutFavoriteList =
                    companies.filter { !it.isFavorite }.sortedBy { it.company.shortName }
                        .reversed()
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.SEC_ID_DESC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }.sortedBy { it.company.secId }.reversed()
                        .toMutableList()
                val withOutFavoriteList =
                    companies.filter { !it.isFavorite }.sortedByDescending { it.company.secId }
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.PRICE_ASC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }
                        .sortedWith(
                            compareBy({ it.company.changePricePercent },
                                { it.company.shortName })
                        ).reversed()
                        .toMutableList()
                val withOutFavoriteList = companies.filter { !it.isFavorite }
                    .sortedWith(
                        compareBy({ it.company.changePricePercent },
                            { it.company.shortName })
                    ).reversed()
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.PRICE_DESC_FAV_TRUE -> {
                val favoriteList =
                    companies.filter { it.isFavorite }
                        .sortedWith(
                            compareBy({ it.company.changePricePercent },
                                { it.company.shortName })
                        )
                        .toMutableList()
                val withOutFavoriteList = companies.filter { !it.isFavorite }
                    .sortedWith(
                        compareBy({ it.company.changePricePercent },
                            { it.company.shortName })
                    )
                favoriteList.addAll(withOutFavoriteList)
                favoriteList.toList()
            }
            StateSort.NAME_ASC_FAV_FALSE -> {
                companies.sortedBy { it.company.shortName }
            }
            StateSort.SEC_ID_ASC_FAV_FALSE -> {
                companies.sortedBy { it.company.secId }
            }
            StateSort.NAME_DESC_FAV_FALSE -> {
                companies.sortedByDescending { it.company.shortName }
            }
            StateSort.SEC_ID_DESC_FAV_FALSE -> {
                companies.sortedByDescending { it.company.secId }
            }
            StateSort.PRICE_ASC_FAV_FALSE -> {
                companies.sortedWith(
                    compareBy({ it.company.changePricePercent },
                        { it.company.shortName })
                ).reversed()
            }
            StateSort.PRICE_DESC_FAV_FALSE -> {
                companies.sortedWith(
                    compareBy({ it.company.changePricePercent },
                        { it.company.shortName })
                )
            }

        }

}