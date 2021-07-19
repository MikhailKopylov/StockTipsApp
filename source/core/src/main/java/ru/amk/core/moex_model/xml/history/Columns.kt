package ru.amk.core.moex_model.xml.history

import retrofit2.http.Path

data class Columns(
    @Path("column")
    val column: MutableList<Column>
)