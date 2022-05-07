package ru.amk.core.moex_model.company.json

import ru.amk.core.moex_model.company.json.metadata.INDEX
import ru.amk.core.moex_model.company.json.metadata.PAGESIZE
import ru.amk.core.moex_model.company.json.metadata.TOTAL

data class MetadataX(
    val INDEX: INDEX,
    val TOTAL: TOTAL,
    val PAGESIZE: PAGESIZE,
)