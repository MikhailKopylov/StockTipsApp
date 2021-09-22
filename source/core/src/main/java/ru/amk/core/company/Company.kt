package ru.amk.core.company

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyWithoutChangePrice(
    val shortName: String,
    val secId: String,
    val date:String,
    val lastPrice:Double
):Parcelable


@Parcelize
data class Company(
    val shortName: String,
    val secId: String,
    val date:String,
    val lastPrice:Double,
    val changePrice:Double,
    val changePricePercent:Double
):Parcelable

