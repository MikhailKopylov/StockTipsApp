package ru.amk.core.moex_model.company.json.metadata

import ru.amk.core.moex_model.company.json.MetadataX

data class HistoryCursor(
    val columns: List<String>,
    val data: List<List<Int>>,
    val metadata: MetadataX
)