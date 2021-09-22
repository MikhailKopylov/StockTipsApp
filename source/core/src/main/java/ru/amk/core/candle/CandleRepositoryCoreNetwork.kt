package ru.amk.core.candle

import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Single
import ru.amk.core.moex_model.company.CreateMoexCandle
import ru.amk.core.moex_model.company.MoexCandleServiceNetwork
import ru.amk.core.moex_model.company.MoexData
import javax.inject.Inject

class CandleRepositoryCoreNetwork @Inject constructor(private val moexCandleServiceNetwork: MoexCandleServiceNetwork) :
    CandleRepositoryCore {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCandleList(
        secId: String,
        dataFrom: String,
        dataTill: String
    ): Single<List<Candle>> {
        val listCompany = mutableListOf<Candle>()
        return moexCandleServiceNetwork
            .getMoexCandleServiceByCompany(secId, dataFrom, dataTill)
            .flatMap { moexCandleRaw ->
                val moexCandle = CreateMoexCandle(moexCandleRaw)
                for (item in moexCandle.convertFromRaw()) {
                    val colorCandle = setColorCandle(item)
                    listCompany.add(
                        with(item) {
                            Candle(HIGH, LOW, OPEN, LEGALCLOSEPRICE, TRADEDATE, colorCandle)
                        }
                    )
                }
                Single.just(listCompany)
            }
    }

    private fun setColorCandle(item: MoexData) =
        if (item.OPEN < item.LEGALCLOSEPRICE) {
            ColorCandle.UP
        } else {
            ColorCandle.DOWN
        }
}