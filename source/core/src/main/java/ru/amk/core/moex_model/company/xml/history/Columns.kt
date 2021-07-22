package ru.amk.core.moex_model.company.xml.history

import retrofit2.http.Path

data class Columns(
    @Path("column")
    val column: MutableList<Column>
)