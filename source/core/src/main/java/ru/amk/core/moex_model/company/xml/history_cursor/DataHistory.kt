package ru.amk.core.moex_model.company.xml.history_cursor

data class DataHistory(
    val metadataHistory: MetadataHistory,
    val rowsHistory: RowsHistory,
    val id:String,
)
