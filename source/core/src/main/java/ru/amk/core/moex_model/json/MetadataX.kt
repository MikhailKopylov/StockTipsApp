package ru.amk.core.moex_model.json

import ru.amk.core.moex_model.json.metadata.INDEX
import ru.amk.core.moex_model.json.metadata.PAGESIZE
import ru.amk.core.moex_model.json.metadata.TOTAL

data class MetadataX(
    val INDEX: INDEX,
    val PAGESIZE: PAGESIZE,
    val TOTAL: TOTAL
)