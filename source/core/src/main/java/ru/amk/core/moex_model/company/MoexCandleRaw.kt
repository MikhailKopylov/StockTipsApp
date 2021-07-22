package ru.amk.core.moex_model.company

import com.google.gson.annotations.SerializedName
import ru.amk.core.moex_model.company.json.History
import ru.amk.core.moex_model.company.json.metadata.HistoryCursor

data class MoexCandleRaw(
    val history: History,
    @SerializedName("history.cursor")
    val cursor: HistoryCursor
)