package ru.amk.core.favorite_company.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [RoomCompany::class], version = 2, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {

    abstract fun favoriteCompanyDAO(): FavoriteCompanyDAO


}
    internal val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE tasks ADD COLUMN lastPrice REAL DEFAULT 0 NOT NULL")
        }
    }
