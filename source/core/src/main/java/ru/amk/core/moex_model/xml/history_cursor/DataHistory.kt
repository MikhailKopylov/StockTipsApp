package ru.amk.core.moex_model.xml.history_cursor

data class DataHistory(
    val metadataHistory: MetadataHistory,
    val rowsHistory: RowsHistory,
    val id:String,
)
