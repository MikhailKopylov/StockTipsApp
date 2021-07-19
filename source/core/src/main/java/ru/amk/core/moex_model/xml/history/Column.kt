package ru.amk.core.moex_model.xml.history

data class Column(
    val name: String,
    val type: String,
    val bytes: Int,
    val max_size: Int
)