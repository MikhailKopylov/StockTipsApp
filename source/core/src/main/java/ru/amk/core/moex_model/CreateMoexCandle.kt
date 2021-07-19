package ru.amk.core.moex_model

class CreateMoexCandle(private val moexCandleRaw: MoexCandleRaw) {

    fun convertFromRaw(): List<MoexCandle> {

        val list = mutableListOf<MoexCandle>()
        for (item in moexCandleRaw.history.data) {
            if (isExistStockQuote(item) && isExistDateAndName(item)) {
                list.add(
                    MoexCandle(
                        (item[15] ?: DEFAULT_DOUBLE) as Double,//ADMITTEDQUOTE: Double,
                        (item[18] ?: DEFAULT_DOUBLE) as Double,//ADMITTEDVALUE: Double,
                        (item[0] ?: DEFAULT_STRING) as String,//BOARDID: String,
                        (item[11] ?: DEFAULT_DOUBLE) as Double,//CLOSE: Double,
                        (item[8] ?: DEFAULT_DOUBLE) as Double,//HIGH: Double,
                        (item[9] ?: DEFAULT_DOUBLE) as Double,//LEGALCLOSEPRICE: Double,
                        (item[7] ?: DEFAULT_DOUBLE) as Double,//LOW: Double,
                        (item[13] ?: DEFAULT_DOUBLE) as Double,//MARKETPRICE2: Double,
                        (item[14] ?: DEFAULT_DOUBLE) as Double,//MARKETPRICE3: Double,
                        (item[17] ?: DEFAULT_DOUBLE) as Double,//MARKETPRICE3TRADESVALUE: Double,
                        (item[16] ?: DEFAULT_DOUBLE) as Double,//MP2VALTRD: Double,
                        (item[4] ?: DEFAULT_DOUBLE) as Double,//NUMTRADES: Double,
                        (item[6] ?: DEFAULT_DOUBLE) as Double,//OPEN: Double,
                        (item[3] ?: DEFAULT_STRING) as String,//SECID: String,
                        (item[2] ?: DEFAULT_STRING) as String,//SHORTNAME: String,
                        (item[1] ?: DEFAULT_STRING) as String,//TRADEDATE: String,
                        ((item[20] ?: DEFAULT_DOUBLE) as Double).toInt(),//TRADINGSESSION: Int,
                        (item[5] ?: DEFAULT_DOUBLE) as Double,//VALUE: Double,
                        (item[12] ?: DEFAULT_DOUBLE) as Double,//VOLUME: Double,
                        (item[10] ?: DEFAULT_DOUBLE) as Double,//WAPRICE: Double,
                        (item[19] ?: DEFAULT_DOUBLE) as Double,//WAVAL: Double
                    )
                )
            }
        }

        return list
    }

    private fun isExistStockQuote(rawData: List<Any>): Boolean {
        when (DEFAULT_DOUBLE) {
            rawData[11], rawData[8], rawData[7], rawData[6] -> return false
        }
        return true
    }

    private fun isExistDateAndName(rawData: List<Any>): Boolean {
        when (DEFAULT_STRING) {
            rawData[2], rawData[1], rawData[3] -> return false
        }
        return true
    }

}