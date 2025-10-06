package com.aquaintel.app.ui.screens.main.forecast

import com.aquaintel.app.data.models.PredictionData

data class ForecastUiState(
    val isLoading: Boolean = true,
    val predictions: List<PredictionData> = emptyList(),
    val selectedStationId: String = "",
    val forecastPeriod: ForecastPeriod = ForecastPeriod.MONTH,
    val seasonalForecast: Map<String, String> = emptyMap(),
    val riskAssessment: String = "",
    val error: String? = null
)

enum class ForecastPeriod(val displayName: String, val days: Int) {
    WEEK("1 Week", 7),
    MONTH("1 Month", 30),
    QUARTER("3 Months", 90)
}
