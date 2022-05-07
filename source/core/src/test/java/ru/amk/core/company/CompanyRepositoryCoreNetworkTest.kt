package ru.amk.core.company

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito
import ru.amk.core.moex_model.company.MoexCandleService
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexCandleServiceNetworkImpl
import ru.amk.core.moex_model.company.MoexCompanyRaw
import ru.amk.core.moex_model.company.json.History
import ru.amk.core.moex_model.company.json.Metadata
import ru.amk.core.moex_model.company.json.MetadataX
import ru.amk.core.moex_model.company.json.metadata.*

class CompanyRepositoryCoreNetworkTest {

    private val moexCandleService = Mockito.spy(MoexCandleService::class.java)
    private val test  = MoexCompanyRaw(
        History(
            emptyList(),
            listOf(
                listOf("TQBR","2021-09-25","АбрауДюрсо","ABRD",139.0,1010250.0,192.0,191.5,192.5,192.5,192.0,192.5,5260.0,192.0,192.0,192.5,1010250.0,1010250.0,1010250.0,0.0,3.0),
                listOf("TQBR","2021-09-25","Аэрофлот","AFLT",14198.0,339356276.0,67.12,67.12,68.5,67.82,67.84,67.84,5002430,67.84,67.84,67.82,287492116.0,287492116.0,287492116.0,0.0,3.0),
                listOf("TQBR", "2021-10-25", "Аптеки36и6", "APTK", 1755, 5082955.84, 13.68, 13.536, 13.798, 13.566, 13.604, 13.566, 373620, 13.604, 13.604, 13.566, 5082955.84, 5082955.84, 5082955.84, 0, 3),
                listOf("TQBR", "2021-10-25", "АстрЭнСб", "ASSB", 68, 257935, 1.0495, 1.014, 1.0495, 1.0495, 1.036, 1.0495, 249000, 0.0, 1.0405, 1.0495, 0, 503691.5, 0, 0, 3),
                listOf("TQBR", "2021-10-25", "ГАЗПРОМ ао", "GAZP", 95004, 20765248577.4, 359.99, 356.69, 365.84, 364.04, 362.13, 364.84, 57341870, 361.96, 361.96, 364.04, 19330461673.3, 19330461673.3, 19330461673.3, 0, 3),
            ),
            Metadata(
                ADMITTEDQUOTE(""),
                ADMITTEDVALUE(""),
                BOARDID(0, 0, ""),
                CLOSE(""),
                HIGH(""),
                LEGALCLOSEPRICE(""),
                LOW(""),
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
                    (0, 0, ""),
                TRADEDATE
                    (0, 0, ""),
                TRADINGSESSION
                    (""),
                VALUE
                    (""),
                VOLUME
                    (""),
                WAPRICE
                    (""),
                WAVAL
                    ("")
            )
        ),
        HistoryCursor(
            emptyList(),
            emptyList(),
            MetadataX(INDEX(""), TOTAL("5"), PAGESIZE("13"))
        )
    )


    @Test
    fun getCompanyList() {
        val date = "2021-09-25"
        Mockito.`when`(moexCandleService.getCompaniesByPage(0,date)).thenReturn(ApiMoexRequest.listCompanyPage1)
        Mockito.`when`(moexCandleService.getCompaniesByPage(5,date)).thenReturn(ApiMoexRequest.listCompanyPage2)
        Mockito.`when`(moexCandleService.getCompaniesByPage(10,date)).thenReturn(ApiMoexRequest.listCompanyPage3)
        val moexCandle: MoexCandleServiceNetwork = MoexCandleServiceNetworkImpl(moexCandleService)
        moexCandle.getAllCompany(0, date).test().assertValue{
            val t = 0
            it == test
        }
        val companyRepositoryCore:CompanyRepositoryCore = CompanyRepositoryCoreNetwork(moexCandle)

//        companyRepositoryCore.getCompanyList(date)
//            .test()
//            .assertValue{
//                it == listOf<Company>()
//            }




        

    }

    @Test
    fun getCurrentDate() {
    }


}


object ApiMoexRequest {

    val listCompanyPage1: Single<MoexCompanyRaw> = Single.just(MoexCompanyRaw(
                    History(
                        emptyList(),
                        listOf(
                            listOf("TQBR","2021-09-25","АбрауДюрсо","ABRD",139.0,1010250.0,192.0,191.5,192.5,192.5,192.0,192.5,5260.0,192.0,192.0,192.5,1010250.0,1010250.0,1010250.0,0.0,3.0),
                            listOf("TQBR","2021-09-25","Аэрофлот","AFLT",14198.0,339356276.0,67.12,67.12,68.5,67.82,67.84,67.84,5002430,67.84,67.84,67.82,287492116.0,287492116.0,287492116.0,0.0,3.0),
                            listOf("TQBR", "2021-10-25", "Аптеки36и6", "APTK", 1755, 5082955.84, 13.68, 13.536, 13.798, 13.566, 13.604, 13.566, 373620, 13.604, 13.604, 13.566, 5082955.84, 5082955.84, 5082955.84, 0, 3),
                            listOf("TQBR", "2021-10-25", "АстрЭнСб", "ASSB", 68, 257935, 1.0495, 1.014, 1.0495, 1.0495, 1.036, 1.0495, 249000, 0.0, 1.0405, 1.0495, 0, 503691.5, 0, 0, 3),
                            listOf("TQBR", "2021-10-25", "ГАЗПРОМ ао", "GAZP", 95004, 20765248577.4, 359.99, 356.69, 365.84, 364.04, 362.13, 364.84, 57341870, 361.96, 361.96, 364.04, 19330461673.3, 19330461673.3, 19330461673.3, 0, 3),
                        ),
                        Metadata(
                            ADMITTEDQUOTE(""),
                            ADMITTEDVALUE(""),
                            BOARDID(0, 0, ""),
                            CLOSE(""),
                            HIGH(""),
                            LEGALCLOSEPRICE(""),
                            LOW(""),
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
                                (0, 0, ""),
                            TRADEDATE
                                (0, 0, ""),
                            TRADINGSESSION
                                (""),
                            VALUE
                                (""),
                            VOLUME
                                (""),
                            WAPRICE
                                (""),
                            WAVAL
                                ("")
                        )
                    ),
                    HistoryCursor(
                        emptyList(),
                        listOf(listOf<Int>(0, 13, 5)),
                        MetadataX(INDEX(""), TOTAL("5"), PAGESIZE("13"))
                    )
                )
        )
    val listCompanyPage2: Single<MoexCompanyRaw> = Single.just(MoexCompanyRaw(
                    History(
                        emptyList(),
                        listOf(
                            listOf("TQBR", "2021-10-25", "Куйбазот", "KAZT", 529, 16893448, 402.4, 402.2, 414, 406, 408.2, 406, 41390, 408.2, 408.2, 406, 16893448, 16893448, 16893448, 0, 3),
                            listOf("TQBR", "2021-10-25", "КамчатЭ ао", "KCHE", 613, 3882145, 0.226, 0.2125, 0.24, 0.2215, 0.222, 0.2215, 17480000, 0.222, 0.222, 0.2215, 3882145, 3882145, 3882145, 0, 3),
                            listOf("TQBR", "2021-10-25", "КалужскСК", "KLSB", 2083, 16356552, 12.21, 11.83, 12.77, 12.46, 12.33, 12.46, 1326900, 12.33, 12.33, 12.46, 16356552, 16356552, 16356552, 0, 3),
                            listOf("TQBR", "2021-10-25", "КАМАЗ", "KMAZ", 2548, 24547404, 123.7, 116.5, 124.3, 118, 119.5, 118, 205340, 119.5, 119.5, 118, 24547404, 24547404, 24547404, 0, 3),
                            listOf("TQBR", "2021-10-25", "КоршГОК ао", "KOGK", 49, 6001400, 55200, 55200, 57600, 57000, 56600, 57000, 106, 56600, 56600, 57000, 6001400, 6001400, 6001400, 0, 3),
                        ),
                        Metadata(
                            ADMITTEDQUOTE(""),
                            ADMITTEDVALUE(""),
                            BOARDID(0, 0, ""),
                            CLOSE(""),
                            HIGH(""),
                            LEGALCLOSEPRICE(""),
                            LOW(""),
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
                                (0, 0, ""),
                            TRADEDATE
                                (0, 0, ""),
                            TRADINGSESSION
                                (""),
                            VALUE
                                (""),
                            VOLUME
                                (""),
                            WAPRICE
                                (""),
                            WAVAL
                                ("")
                        )
                    ),
                    HistoryCursor(
                        emptyList(),
                        listOf(listOf<Int>(5, 13, 5)),
                        MetadataX(INDEX(""), TOTAL("5"), PAGESIZE("13"))
                    )
                )
            )


    val listCompanyPage3: Single<MoexCompanyRaw> = Single.just(MoexCompanyRaw(
                    History(
                        emptyList(),
                        listOf(
                            listOf("TQBR", "2021-10-25", "Татнфт 3ао", "TATN", 21410, 2038273574.6, 563.3, 560.3, 574.2, 573, 567.6, 570.2, 3591174, 567.5, 567.5, 573, 1910988092, 1910988092, 1910988092, 0, 3),
                            listOf("TQBR", "2021-10-25", "ВТБ ао", "VTBR", 61549, 3549596305.95, 0.054795, 0.053945, 0.0562, 0.0558, 0.055265, 0.05617, 64228310000, 0.055175, 0.055175, 0.0558, 3112819276.6, 3112819276.6, 3112819276.6, 0, 3),
                            listOf("TQBR", "2021-10-25", "Yandex clA", "YNDX", 21809, 2309532098, 5340, 5319, 5422, 5414.8, 5376.6, 5383, 429565, 5374.6, 5374.6, 5414.8, 2049609068.6, 2049609068.6, 2049609068.6, 0, 3),
                        ),
                        Metadata(
                            ADMITTEDQUOTE(""),
                            ADMITTEDVALUE(""),
                            BOARDID(0, 0, ""),
                            CLOSE(""),
                            HIGH(""),
                            LEGALCLOSEPRICE(""),
                            LOW(""),
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
                                (0, 0, ""),
                            TRADEDATE
                                (0, 0, ""),
                            TRADINGSESSION
                                (""),
                            VALUE
                                (""),
                            VOLUME
                                (""),
                            WAPRICE
                                (""),
                            WAVAL
                                ("")
                        )
                    ),
                    HistoryCursor(
                        emptyList(),
                        listOf(listOf<Int>(10, 13, 5)),
                        MetadataX(INDEX(""), TOTAL("5"), PAGESIZE("13"))
                    )
                )
            )






}