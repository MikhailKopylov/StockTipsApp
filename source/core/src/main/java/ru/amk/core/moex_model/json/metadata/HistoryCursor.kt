package ru.amk.core.moex_model.json.metadata

import ru.amk.core.moex_model.json.MetadataX

data class HistoryCursor(
    val columns: List<String>,
    val data: List<List<Int>>,
    val metadata: MetadataX
)