package ru.amk.core.moex_model.company

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.amk.core.moex_model.company.json.History
import ru.amk.core.moex_model.company.json.Metadata
import ru.amk.core.moex_model.company.json.MetadataX
import ru.amk.core.moex_model.company.json.metadata.*

class CreateMoexCandleTest  {

    @Test
    fun `create moexData`() {
        val sourceMoexCandleRaw = MoexCandleRaw(
            History(
                emptyList(),
                listOf(
                    listOf(
                        "TQBR",
                        "2021-09-24",
                        "АбрауДюрсо",
                        "ABRD",
                        139.0,
                        1010250.0,
                        192.0,
                        191.5,
                        192.5,
                        192.5,
                        192.0,
                        192.5,
                        5260.0,
                        192.0,
                        192.0,
                        192.5,
                        1010250.0,
                        1010250.0,
                        1010250.0,
                        0.0,
                        3.0
                    )
                ),
                Metadata(
                    ADMITTEDQUOTE(""), ADMITTEDVALUE(""), BOARDID(0, 0, ""), CLOSE(""), HIGH(""),
                    LEGALCLOSEPRICE(""), LOW(""),
                    MARKETPRICE2(""),
                    MARKETPRICE3
                        (""),
                    MARKETPRICE3TRADESVALUE
                        (""),
                    MP2VALTRD
                        (""),
                    NUMTRADES
                        (""),
                    OPEN
                        (""),
                    SECID
                        (0, 0, ""),
                    SHORTNAME
                        (0, 0, ""), TRADEDATE
                        (0, 0, ""), TRADINGSESSION
                        (""), VALUE
                        (""),
                    VOLUME
                        (""),
                    WAPRICE
                        (""),
                    WAVAL
                        ("")
                )
            ),
            HistoryCursor(emptyList(), emptyList(), MetadataX(INDEX(""), PAGESIZE(""), TOTAL("")))
        )
        val d = CreateMoexCandle(sourceMoexCandleRaw).convertFromRaw()
        val result = listOf<MoexData>(
            MoexData(
                192.5,
                1010250.0,
                "TQBR",
                192.5,
                192.5,
                192.5,
                191.5,
                192.0,
                192.0,
                1010250.0,
                1010250.0,
                139.0,
                192.0,
                "ABRD",
                "АбрауДюрсо",
                "2021-09-24",
                3,
                1010250.0,
                5260.0,
                192.0,
                0.0

            ))

        assertEquals(d, result)
    }



}