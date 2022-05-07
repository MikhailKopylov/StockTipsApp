package ru.amk.company_list.list.handlers

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


object SortHandler {

    const val STATE_SORT: String = "State sort"

    private val DEFAULT_SORTED = SortedBy.NAME
    private val DEFAULT_ORDER = OrderBy.ASC
    private val DEFAULT_STATE_SORT = StateSort.NAME_ASC_FAV_TRUE
    private const val DEFAULT_FAVORITE_UP = true
    private val sortHandler = StateHandler()

    private val observableSorted = BehaviorSubject.createDefault(DEFAULT_SORTED)
    private val observableFavoriteUp = BehaviorSubject.createDefault(DEFAULT_FAVORITE_UP)

    private var orderBy = DEFAULT_ORDER
    var stateSorting = DEFAULT_STATE_SORT
        private set

    private var favoriteUp = DEFAULT_FAVORITE_UP
    fun changeFavoriteUp() {
        favoriteUp = !favoriteUp
        observableFavoriteUp.onNext(favoriteUp)
    }

    fun setFavoriteUp(isFavoriteUp: Boolean) {
        favoriteUp = isFavoriteUp
        observableFavoriteUp.onNext(favoriteUp)
    }

    private var sortedBy = DEFAULT_SORTED
    fun setSortedBy(sortedBy: SortedBy) {
        if (SortHandler.sortedBy == sortedBy) {
            orderBy = reverseOrder(orderBy)
        } else {
            SortHandler.sortedBy = sortedBy
            orderBy = OrderBy.ASC
        }
        observableSorted.onNext(sortedBy)
    }

    fun getStateSorting(): Flowable<StateSort> =
        Observable.combineLatest(observableSorted, observableFavoriteUp, { _, favoriteUp ->
            stateSorting = sortHandler.toState(sortedBy, orderBy, favoriteUp)
            return@combineLatest stateSorting
        }).toFlowable(BackpressureStrategy.LATEST)

    fun fromState(stateSort: StateSort) {
        when (stateSort) {
            StateSort.NAME_ASC_FAV_TRUE -> {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.ASC
                favoriteUp = true
            }
            StateSort.NAME_ASC_FAV_FALSE -> {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.ASC
                favoriteUp = false
            }
            StateSort.NAME_DESC_FAV_TRUE -> {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.DESC
                favoriteUp = true
            }
            StateSort.NAME_DESC_FAV_FALSE -> {
                sortedBy = SortedBy.NAME
                orderBy = OrderBy.DESC
                favoriteUp = false
            }
            StateSort.SEC_ID_ASC_FAV_TRUE -> {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.ASC
                favoriteUp = true
            }
            StateSort.SEC_ID_ASC_FAV_FALSE -> {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.ASC
                favoriteUp = false
            }
            StateSort.SEC_ID_DESC_FAV_TRUE -> {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.DESC
                favoriteUp = true
            }
            StateSort.SEC_ID_DESC_FAV_FALSE -> {
                sortedBy = SortedBy.SEC_ID
                orderBy = OrderBy.DESC
                favoriteUp = false
            }
            StateSort.PRICE_ASC_FAV_TRUE -> {
                sortedBy = SortedBy.PRICE
                orderBy = OrderBy.ASC
                favoriteUp = true
            }
            StateSort.PRICE_ASC_FAV_FALSE -> {
                sortedBy = SortedBy.PRICE
                orderBy = OrderBy.ASC
                favoriteUp = false
            }
            StateSort.PRICE_DESC_FAV_TRUE -> {
                sortedBy = SortedBy.PRICE
                orderBy = OrderBy.DESC
                favoriteUp = true
            }
            StateSort.PRICE_DESC_FAV_FALSE -> {
                sortedBy = SortedBy.PRICE
                orderBy = OrderBy.DESC
                favoriteUp = false
            }
        }
    }

    private fun reverseOrder(orderBy: OrderBy): OrderBy = if (orderBy == OrderBy.ASC) {
        OrderBy.DESC
    } else {
        OrderBy.ASC
    }


}