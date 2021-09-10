package ru.amk.core.favorite_company.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class RoomCompany(
    val shortName: String,
    val secId: String,
    val date:String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
