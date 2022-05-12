package ru.amk.core

import org.junit.Assert.*
import org.junit.Test

class PriceFormatTest {

    @Test
    fun `test priceFormat last price between 0 and 1`() {
        val price = 0.225978

        val result = price.priceFormat(0.9)

        assertEquals("+ 0,225978", result)
    }

    @Test
    fun `test priceFormat last price between 1 and 100`() {
        val price = 1.2259

        val result = price.priceFormat(1.9)

        assertEquals("+ 1,226", result)
        assertNotEquals("+ 1,2259", result)
    }

    @Test
    fun `test priceFormat price is null`() {
        val price = null

        val result = price?.priceFormat(1.9)

        assertNull(result)
    }

    @Test
    fun `test priceFormat price and last price not null`() {
        val price = 1.2259

        val result = price.priceFormat(1.9)

        assertNotNull(result)
    }


}