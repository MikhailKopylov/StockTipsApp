package ru.amk.core.moex_model.xml

import ru.amk.core.moex_model.xml.history.Columns
import ru.amk.core.moex_model.xml.history.Rows

data class Data(
    val metadata: Metadata,
    val rows: Rows,
    val id: String,
    val text: String,
    val columns: Columns
)