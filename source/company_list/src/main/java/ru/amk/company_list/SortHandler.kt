package ru.amk.company_list


enum class SortedBy {
    NAME, SEC_ID
}

enum class OrderBy {
    RIGHT, REVERS
}

class SortHandler(
    private val listCompany: List<FavoriteCompany>
) {

    enum class StateSort {
        NAME_RIGHT_FAV_TRUE, NAME_RIGHT_FAV_FALSE, NAME_REVERSE_FAV_TRUE, NAME_REVERSE_FAV_FALSE,
        SEC_ID_RIGHT_FAV_TRUE, SEC_ID_RIGHT_FAV_FALSE, SEC_ID_REVERSE_FAV_TRUE, SEC_ID_REVERSE_FAV_FALSE
    }

    fun sort(sortedBy: SortedBy, orderBy: OrderBy, isFavoriteUp: Boolean): List<FavoriteCompany> =
        when (identStateSort(sortedBy, orderBy, isFavoriteUp)) {
            StateSort.NAME_RIGHT_FAV_TRUE -> {
                val favoriteList =
                    listCompany.filter { it.isFavorite }.sortedBy { it.company.shortName }
                        .toMutableList()
                val withOutFavoriteList =
                    listCompany.filter { !it.isFavorite }.sortedBy { it.company.shortName }
                favoriteList.addAll(withOutFavoriteList)
                Settings.stateSorting = StateSort.NAME_RIGHT_FAV_TRUE
                favoriteList.toList()
            }
            StateSort.SEC_ID_RIGHT_FAV_TRUE -> {
                val favoriteList =
                    listCompany.filter { it.isFavorite }.sortedBy { it.company.secId }
                        .toMutableList()
                val withOutFavoriteList =
                    listCompany.filter { !it.isFavorite }.sortedBy { it.company.secId }
                favoriteList.addAll(withOutFavoriteList)
                Settings.stateSorting = StateSort.SEC_ID_RIGHT_FAV_TRUE
                favoriteList.toList()
            }
            StateSort.NAME_REVERSE_FAV_TRUE -> {
                val favoriteList =
                    listCompany.filter { it.isFavorite }.sortedBy { it.company.shortName }
                        .reversed()
                        .toMutableList()
                val withOutFavoriteList =
                    listCompany.filter { !it.isFavorite }.sortedBy { it.company.shortName }
                        .reversed()
                favoriteList.addAll(withOutFavoriteList)
                Settings.stateSorting = StateSort.NAME_REVERSE_FAV_TRUE
                favoriteList.toList()
            }
            StateSort.SEC_ID_REVERSE_FAV_TRUE -> {
                val favoriteList =
                    listCompany.filter { it.isFavorite }.sortedBy { it.company.secId }.reversed()
                        .toMutableList()
                val withOutFavoriteList =
                    listCompany.filter { !it.isFavorite }.sortedBy { it.company.secId }.reversed()
                favoriteList.addAll(withOutFavoriteList)
                Settings.stateSorting = StateSort.SEC_ID_REVERSE_FAV_TRUE
                favoriteList.toList()
            }
            StateSort.NAME_RIGHT_FAV_FALSE -> {
                Settings.stateSorting = StateSort.NAME_RIGHT_FAV_FALSE
                listCompany.sortedBy { it.company.shortName }
            }
            StateSort.SEC_ID_RIGHT_FAV_FALSE -> {
                Settings.stateSorting = StateSort.SEC_ID_RIGHT_FAV_FALSE
                listCompany.sortedBy { it.company.secId }
            }
            StateSort.NAME_REVERSE_FAV_FALSE -> {
                Settings.stateSorting = StateSort.NAME_REVERSE_FAV_FALSE
                listCompany.sortedBy { it.company.shortName }.reversed()
            }
            StateSort.SEC_ID_REVERSE_FAV_FALSE -> {
                Settings.stateSorting = StateSort.SEC_ID_REVERSE_FAV_FALSE
                listCompany.sortedBy { it.company.secId }.reversed()
            }
        }

    companion object {
        fun identStateSort(
            sortedBy: SortedBy,
            orderBy: OrderBy,
            isFavoriteUp: Boolean
        ) = when (sortedBy) {
            SortedBy.NAME -> {
                when (orderBy) {
                    OrderBy.RIGHT -> {
                        if (isFavoriteUp) StateSort.NAME_RIGHT_FAV_TRUE
                        else StateSort.NAME_RIGHT_FAV_FALSE
                    }
                    OrderBy.REVERS -> {
                        if (isFavoriteUp) StateSort.NAME_REVERSE_FAV_TRUE
                        else StateSort.NAME_REVERSE_FAV_FALSE
                    }
                }
            }
            SortedBy.SEC_ID -> {
                when (orderBy) {
                    OrderBy.RIGHT -> {
                        if (isFavoriteUp) StateSort.SEC_ID_RIGHT_FAV_TRUE
                        else StateSort.SEC_ID_RIGHT_FAV_FALSE
                    }
                    OrderBy.REVERS -> {
                        if (isFavoriteUp) StateSort.SEC_ID_REVERSE_FAV_TRUE
                        else StateSort.SEC_ID_REVERSE_FAV_FALSE
                    }
                }

            }
        }

    }

}