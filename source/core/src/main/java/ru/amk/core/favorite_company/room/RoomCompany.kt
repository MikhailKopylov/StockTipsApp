package ru.amk.core.favorite_company.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class RoomCompany(
    val shortName: String,
    @PrimaryKey()
    val secId: String,
    val date:String,
    val lastPrice:Double,
    val changePrice:Double,
    val changePricePercent:Double
){
//    @PrimaryKey(autoGenerate = true)
//    var id: Int? = null
}
