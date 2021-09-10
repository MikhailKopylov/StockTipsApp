package ru.amk.company_list

import ru.amk.core.company.Company

data class FavoriteCompany(
    val company:Company,
    val isFavorite:Boolean
)
