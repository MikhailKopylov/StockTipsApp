package ru.amk.company_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.amk.core.company.Company

@Parcelize
data class FavoriteCompany(
    val company:Company,
    val isFavorite:Boolean
): Parcelable
