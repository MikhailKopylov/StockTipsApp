package ru.amk.core

import org.junit.Assert.*

import org.junit.Test
import ru.amk.core.candle.Candle
import ru.amk.core.candle.ColorCandle

class MinCandleListTest {

    private val candleList = mutableListOf<Candle>()
    private val candle1 = Candle(1.5, 0.2, 11.4, 13.2, "05.05.2019", ColorCandle.DOWN)
    private val candle2 = Candle(10.5, 0.12, 21.4, 12.2, "05.05.2019", ColorCandle.DOWN)
    private val candle3 = Candle(100.5, 0.22, 31.4, 11.2, "05.05.2019", ColorCandle.DOWN)

    @Test
    fun `test minCandleList list is empty`() {
        val result = candleList.minCandleList()

        assertEquals(result, 0.0, 0.0)
    }

    @Test
    fun `test minCandleList in list only positive numbers`() {
        candleList.add(candle1)
        candleList.add(candle2)
        candleList.add(candle3)

        val result = candleList.minCandleList()

        assertEquals(result, 0.0, 0.0)
    }
}