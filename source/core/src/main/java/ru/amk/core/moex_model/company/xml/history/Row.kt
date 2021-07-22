package ru.amk.core.moex_model.company.xml.history


data class Row(
    val BOARDID: String,
    val TRADEDATE: String,
    val SHORTNAME: String,
    val SECID: String,
    val NUMTRADES: Double,
    val VALUE: Double,
    val OPEN: Double,
    val LOW: Double,
    val HIGH: Double,
    val LEGALCLOSEPRICE: Double,
    val WAPRICE: Double,
    val CLOSE: Double,
    val VOLUME: Double,
    val MARKETPRICE2: Double,
    val MARKETPRICE3: Double,
    val ADMITTEDQUOTE: Double,
    val MP2VALTRD: Double,
    val MARKETPRICE3TRADESVALUE: Double,
    val ADMITTEDVALUE: Double,
    val WAVAL: Double,
    val TRADINGSESSION: Int,
)