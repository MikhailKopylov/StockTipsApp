package ru.amk.company_list


object Settings {

    const val FAVORITE_UP_KEY = "FavoriteUpMode"
    const val SORT_BY_KEY = "SortBy"

    private val DEFAULT_SORTED = SortedBy.NAME
    private val DEFAULT_ORDER = OrderBy.RIGHT
    private val DEFAULT_STATE_SORT = SortHandler.StateSort.NAME_RIGHT_FAV_TRUE
    private const val DEFAULT_FAVORITE_UP = false

    var favoriteUp = DEFAULT_FAVORITE_UP
    var sortedBy = DEFAULT_SORTED
    var orderBy = DEFAULT_ORDER

    var stateSorting = DEFAULT_STATE_SORT

}