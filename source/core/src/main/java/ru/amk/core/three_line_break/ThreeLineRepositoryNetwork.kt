package ru.amk.core.three_line_break

import io.reactivex.Single
import ru.amk.core.candle.ColorCandle
import ru.amk.core.moex_model.company.CreateMoexCandle
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import javax.inject.Inject

class ThreeLineRepositoryNetwork @Inject constructor(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
    ThreeLineRepository {


    override fun getTreeLineList(
        secId: String,
        dateFrom: String,
        dateTill: String
    ): Single<List<ThreeLineBreak>> {
        return moexCandleServiceNetwork
            .getMoexCandleServiceByCompany(secId, dateFrom, dateTill)
            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                val moexCandleList = moexCandle.convertFromRaw()
                val threeLineBreak: ThreeLineBreak
                with(moexCandleList[0]) {
                    threeLineBreak =
                        ThreeLineBreak(LEGALCLOSEPRICE, LEGALCLOSEPRICE, TRADEDATE, ColorCandle.UP)
                }
                val result: MutableList<ThreeLineBreak> = mutableListOf(threeLineBreak)
                for (item in moexCandleList) {
                    val maxPrice = result.last().maxPrice
                    val minPrice = result.last().minPrice
                    if (item.LEGALCLOSEPRICE > maxPrice) {
                        result.add(
                            ThreeLineBreak(
                                item.LEGALCLOSEPRICE,
                                maxPrice,
                                item.TRADEDATE,
                                ColorCandle.UP
                            )
                        )
                    } else if (item.LEGALCLOSEPRICE < minPrice) {
                        result.add(
                            ThreeLineBreak(
                                minPrice,
                                item.LEGALCLOSEPRICE,
                                item.TRADEDATE,
                                ColorCandle.DOWN
                            )
                        )
                    }
                }
                Single.just(result)
            }
    }


}