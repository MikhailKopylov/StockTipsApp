package ru.amk.core.moex_model

import com.google.gson.annotations.SerializedName
import ru.amk.core.moex_model.json.History
import ru.amk.core.moex_model.json.metadata.HistoryCursor

data class MoexCandleRaw(
    val history: History,
    @SerializedName("history.cursor")
    val cursor: HistoryCursor
)