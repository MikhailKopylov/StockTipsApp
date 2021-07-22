package ru.amk.core.candle

import io.reactivex.Single
import ru.amk.core.moex_model.company.CreateMoexCandle
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexData

class CandleRepositoryCoreNetwork(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
    CandleRepositoryCore {

    override fun getCandleList(): Single<List<Candle>> {
        val listCompany = mutableListOf<Candle>()
        return moexCandleServiceNetwork
            .getMoexCandleServiceByCompany()
            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    val colorCandle = setColorCandle(item)
                    listCompany.add(Candle(item.HIGH, item.LOW, item.OPEN, item.CLOSE, item.TRADEDATE, colorCandle))
                }
                Single.just(listCompany)
            }
    }

    private fun setColorCandle(item: MoexData) =
        if (item.OPEN > item.CLOSE) {
            ColorCandle.WHITE
        } else {
            ColorCandle.BLACK
        }
}