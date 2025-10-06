package com.aquaintel.app.ui.screens.main.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.mock.MockStations
import com.aquaintel.app.data.mock.MockTimeSeriesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState: StateFlow<DashboardUiState> = _dashboardUiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _dashboardUiState.update { it.copy(isLoading = true) }

            delay(1000) // Simulate loading

            // Mock dashboard data
            val nearbyStations = MockStations.stations.take(5)
            val primaryStation = nearbyStations.firstOrNull()
            val recentData = primaryStation?.let {
                MockTimeSeriesData.getRecentData(it.id, 7)
            } ?: emptyList()

            _dashboardUiState.update {
                it.copy(
                    isLoading = false,
                    currentWaterLevel = primaryStation?.currentWaterLevel ?: 7.2,
                    aquiferStress = primaryStation?.aquiferStress?.name?.lowercase()?.replaceFirstChar { char ->
                        char.uppercase()
                    } ?: "Low",
                    soilQualityIndex = Random.nextDouble(6.0, 10.0),
                    recentData = recentData,
                    nearbyStations = nearbyStations,
                    alerts = generateMockAlerts(),
                    conservationTips = generateConservationTips()
                )
            }
        }
    }

    private fun generateMockAlerts(): List<String> {
        return listOf(
            "Water level declining in 3 nearby stations",
            "Seasonal recharge expected next month",
            "High stress aquifer detected 5km away"
        )
    }

    private fun generateConservationTips(): List<String> {
        return listOf(
            "Install rainwater harvesting systems",
            "Use drip irrigation for agriculture",
            "Regular maintenance of bore wells",
            "Monitor groundwater quality regularly"
        )
    }

    fun refreshData() {
        loadDashboardData()
    }
}
