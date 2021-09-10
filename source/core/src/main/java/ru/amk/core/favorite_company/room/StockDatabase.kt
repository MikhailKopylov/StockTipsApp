package ru.amk.core.favorite_company.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RoomCompany::class], version = 1, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {

    abstract fun favoriteCompanyDAO(): FavoriteCompanyDAO

}
