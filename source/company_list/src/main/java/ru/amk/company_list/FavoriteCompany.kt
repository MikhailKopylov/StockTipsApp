package ru.amk.company_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.amk.core.company.Company

@Parcelize
data class FavoriteCompany(
    val company:Company,
    val isFavorite:Boolean
): Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavoriteCompany

        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        return company.hashCode()
    }
}
