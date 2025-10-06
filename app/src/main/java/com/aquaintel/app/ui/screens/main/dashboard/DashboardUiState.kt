package com.aquaintel.app.ui.screens.main.dashboard

import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.WaterLevelData

data class DashboardUiState(
    val isLoading: Boolean = true,
    val currentWaterLevel: Double = 0.0,
    val aquiferStress: String = "Low",
    val soilQualityIndex: Double = 0.0,
    val recentData: List<WaterLevelData> = emptyList(),
    val nearbyStations: List<DWLRStation> = emptyList(),
    val alerts: List<String> = emptyList(),
    val conservationTips: List<String> = emptyList(),
    val error: String? = null
)
