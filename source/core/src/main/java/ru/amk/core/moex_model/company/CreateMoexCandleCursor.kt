package ru.amk.core.moex_model.company

import java.lang.IndexOutOfBoundsException

class CreateMoexCandleCursor(private val moexCandleRaw: MoexCandleRaw) {

    private val moexCandleCursor =
        try {
            MoexCandleCursor(
                moexCandleRaw.cursor.data[0][0],
                moexCandleRaw.cursor.data[0][1],
                moexCandleRaw.cursor.data[0][2]
            )
        } catch (e: IndexOutOfBoundsException) {
            MoexCandleCursor(0, 0, 100)
        }

    fun isDataLoad():Boolean{
        if((moexCandleCursor.index + moexCandleCursor.pageSize) < moexCandleCursor.total ) {
            return false
        }
        return true
    }

    fun moexCandleCursorNextPage(): Int= moexCandleCursor.index + moexCandleCursor.pageSize

    fun countPage():Int = moexCandleCursor.total/moexCandleCursor.pageSize

    fun pageSize():Int = moexCandleCursor.pageSize


}