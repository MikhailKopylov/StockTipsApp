package ru.amk.core.company

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val shortName: String,
    val secId: String,
    val date:String,
    val lastPrice:Double
):Parcelable
