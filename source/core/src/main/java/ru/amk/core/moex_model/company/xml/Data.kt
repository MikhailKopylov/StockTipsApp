package ru.amk.core.moex_model.company.xml

import ru.amk.core.moex_model.company.xml.history.Columns
import ru.amk.core.moex_model.company.xml.history.Rows

data class Data(
    val metadata: Metadata,
    val rows: Rows,
    val id: String,
    val text: String,
    val columns: Columns
)