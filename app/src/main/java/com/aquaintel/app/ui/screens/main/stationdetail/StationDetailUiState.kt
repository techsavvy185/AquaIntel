package com.aquaintel.app.ui.screens.main.stationdetail

import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.PredictionData
import com.aquaintel.app.data.models.RainfallData
import com.aquaintel.app.data.models.WaterLevelData

data class StationDetailUiState(
    val isLoading: Boolean = true,
    val station: DWLRStation? = null,
    val waterLevelData: List<WaterLevelData> = emptyList(),
    val rainfallData: List<RainfallData> = emptyList(),
    val predictions: List<PredictionData> = emptyList(),
    val selectedTimeRange: TimeRange = TimeRange.WEEK,
    val error: String? = null
)

enum class TimeRange(val displayName: String, val days: Int) {
    WEEK("7 Days", 7),
    MONTH("30 Days", 30),
    QUARTER("3 Months", 90),
    YEAR("1 Year", 365)
}
