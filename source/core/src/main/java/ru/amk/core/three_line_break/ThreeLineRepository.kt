package ru.amk.core.three_line_break

import io.reactivex.Single
import ru.amk.core.candle.Candle

interface ThreeLineRepository {

    fun getTreeLineList(
        secId: String,
        dateFrom: String,
        dateTill: String
    ): Single<List<ThreeLineBreak>>
}

