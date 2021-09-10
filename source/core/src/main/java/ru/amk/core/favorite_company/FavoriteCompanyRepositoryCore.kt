package ru.amk.core.favorite_company

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.amk.core.company.Company
import ru.amk.core.favorite_company.room.FavoriteCompanyDAO
import ru.amk.core.favorite_company.room.RoomCompany
import ru.amk.core.favorite_company.room.StockDatabase
import javax.inject.Inject

interface FavoriteCompanyRepositoryCore {

    fun getFavoriteCompanyList(): Flowable<Set<Company>>
    fun deleteCompanyFromFavorite(company: Company)
    fun addFavoriteCompany(company: Company)
}

class FavoriteCompanyRepositoryCoreImpl @Inject constructor(private val favoriteCompanyDAO: FavoriteCompanyDAO) :
    FavoriteCompanyRepositoryCore {


    override fun getFavoriteCompanyList(): Flowable<Set<Company>> =
        favoriteCompanyDAO.getAllCompany()
//            .subscribeOn(Schedulers.io())
            .map { listRoomCompany ->
                listRoomCompany
                    .map { Company(it.shortName, it.secId, it.date) }.toSet()
            }

    override fun deleteCompanyFromFavorite(company: Company) {
        val roomCompany = with(company) {
            RoomCompany(shortName, secId, date)
        }
        Completable
            .fromAction{favoriteCompanyDAO.deleteCompany(roomCompany.secId)}
            .subscribeOn(Schedulers.io())
            .subscribe()
    }


    override fun addFavoriteCompany(company: Company) {
        val roomCompany = with(company) {
            RoomCompany(shortName, secId, date)
        }
        Completable
            .fromAction { favoriteCompanyDAO.add(roomCompany) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}