package ru.amk.core.moex_model.company.json


data class History(
    val columns: List<String>,
    val data: List<List<Any>>,
    val metadata: Metadata
)