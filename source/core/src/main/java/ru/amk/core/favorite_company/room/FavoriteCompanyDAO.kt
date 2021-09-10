package ru.amk.core.favorite_company.room

import androidx.room.*
import androidx.room.Database
import io.reactivex.Flowable

@Dao
interface FavoriteCompanyDAO {

    @Insert
    fun add(company: RoomCompany)

    @Insert
    fun addAll(vararg company: RoomCompany)

    @Query("DELETE FROM companies WHERE secId=(:secId)")
    fun deleteCompany(secId: String)

    @Query("SELECT * FROM companies")
    fun getAllCompany(): Flowable<List<RoomCompany>>

    @Query("SELECT * FROM companies WHERE secId=(:secId) LIMIT 1")
    fun getCompany(secId: String): RoomCompany
}

