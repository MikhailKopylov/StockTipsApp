package ru.amk.company_list

import ru.amk.company_list.list.SortedBy

object Settings {

    const val FAVORITE_UP_KEY = "FavoriteUpMode"
    const val SORT_BY_KEY = "SortBy"

    private val DEFAULT_SORTED = SortedBy.NAME
    private const val DEFAULT_FAVORITE_UP = false

    var favoriteUp = DEFAULT_FAVORITE_UP
    var sortedBy = DEFAULT_SORTED
}