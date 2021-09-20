package ru.amk.core.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.amk.core.favorite_company.room.FavoriteCompanyDAO
import ru.amk.core.favorite_company.room.MIGRATION_1_2
import ru.amk.core.favorite_company.room.StockDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {


    @Provides
    @Singleton
    fun provideStockDatabase(context: Context): StockDatabase = Room
            .databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stock_database"
            )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun bindFavoriteCompanyDAO(db: StockDatabase): FavoriteCompanyDAO = db.favoriteCompanyDAO()


}