package ru.amk.core.company

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyWithoutChangePrice(
    val shortName: String,
    val secId: String,
    val date:String,
    val lastPrice:Double
):Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (secId != other.secId) return false

        return true
    }

    override fun hashCode(): Int {
        return secId.hashCode()
    }
}


@Parcelize
data class Company(
    val shortName: String,
    val secId: String,
    val date:String,
    val lastPrice:Double,
    val changePrice:Double,
    val changePricePercent:Double
):Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (secId != other.secId) return false

        return true
    }

    override fun hashCode(): Int {
        return secId.hashCode()
    }
}

