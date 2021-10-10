package ru.amk.company_list.list.handlers


enum class SortedBy {
    NAME, SEC_ID, PRICE
}

enum class OrderBy {
    ASC, DESC
}

enum class StateSort {
    NAME_ASC_FAV_TRUE, NAME_ASC_FAV_FALSE, NAME_DESC_FAV_TRUE, NAME_DESC_FAV_FALSE,
    SEC_ID_ASC_FAV_TRUE, SEC_ID_ASC_FAV_FALSE, SEC_ID_DESC_FAV_TRUE, SEC_ID_DESC_FAV_FALSE,
    PRICE_ASC_FAV_TRUE, PRICE_ASC_FAV_FALSE, PRICE_DESC_FAV_TRUE, PRICE_DESC_FAV_FALSE,
}

class StateHandler {

    fun toState(sortedBy: SortedBy, orderBy: OrderBy, isFavoriteUp: Boolean): StateSort =
        when (sortedBy) {
            SortedBy.NAME -> {
                when (orderBy) {
                    OrderBy.ASC -> {
                        if (isFavoriteUp) StateSort.NAME_ASC_FAV_TRUE
                        else StateSort.NAME_ASC_FAV_FALSE
                    }
                    OrderBy.DESC -> {
                        if (isFavoriteUp) StateSort.NAME_DESC_FAV_TRUE
                        else StateSort.NAME_DESC_FAV_FALSE
                    }
                }
            }
            SortedBy.SEC_ID -> {
                when (orderBy) {
                    OrderBy.ASC -> {
                        if (isFavoriteUp) StateSort.SEC_ID_ASC_FAV_TRUE
                        else StateSort.SEC_ID_ASC_FAV_FALSE
                    }
                    OrderBy.DESC -> {
                        if (isFavoriteUp) StateSort.SEC_ID_DESC_FAV_TRUE
                        else StateSort.SEC_ID_DESC_FAV_FALSE
                    }
                }
            }
            SortedBy.PRICE -> {
                when (orderBy) {
                    OrderBy.ASC -> {
                        if (isFavoriteUp) StateSort.PRICE_ASC_FAV_TRUE
                        else StateSort.PRICE_ASC_FAV_FALSE
                    }
                    OrderBy.DESC -> {
                        if (isFavoriteUp) StateSort.PRICE_DESC_FAV_TRUE
                        else StateSort.PRICE_DESC_FAV_FALSE
                    }
                }
            }
        }
}