package ru.amk.core.moex_model

const val DEFAULT_DOUBLE:Double = -1.0
const val DEFAULT_STRING:String = ""

data class MoexCandle(
    val ADMITTEDQUOTE: Double,
    val ADMITTEDVALUE: Double,
    val BOARDID: String,
    val CLOSE: Double,
    val HIGH: Double,
    val LEGALCLOSEPRICE: Double,
    val LOW: Double,
    val MARKETPRICE2: Double,
    val MARKETPRICE3: Double,
    val MARKETPRICE3TRADESVALUE: Double,
    val MP2VALTRD: Double,
    val NUMTRADES: Double,
    val OPEN: Double,
    val SECID: String,
    val SHORTNAME: String,
    val TRADEDATE: String,
    val TRADINGSESSION: Int,
    val VALUE: Double,
    val VOLUME: Double,
    val WAPRICE: Double,
    val WAVAL: Double
)