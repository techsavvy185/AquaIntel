package com.aquaintel.app.ui.screens.main.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.mock.MockStations
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
class ReportViewModel @Inject constructor() : ViewModel() {

    private val _reportUiState = MutableStateFlow(ReportUiState())
    val reportUiState: StateFlow<ReportUiState> = _reportUiState.asStateFlow()

    init {
        loadRecentReports()
    }

    private fun loadRecentReports() {
        viewModelScope.launch {
            delay(500)

            val mockReports = listOf(
                CommunityReport(
                    id = "R001",
                    type = ReportType.WATER_QUALITY,
                    severity = ReportSeverity.HIGH,
                    description = "Unusual taste and color in water from DWLR station",
                    station = "Nagpur Central DWLR",
                    timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                    status = "Investigating"
                ),
                CommunityReport(
                    id = "R002",
                    type = ReportType.EQUIPMENT_FAILURE,
                    severity = ReportSeverity.MEDIUM,
                    description = "Water level sensor showing inconsistent readings",
                    station = "Mumbai West DWLR",
                    timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                    status = "Resolved"
                ),
                CommunityReport(
                    id = "R003",
                    type = ReportType.UNUSUAL_READING,
                    severity = ReportSeverity.LOW,
                    description = "Sudden spike in water level readings after no rainfall",
                    station = "Delhi Central DWLR",
                    timestamp = System.currentTimeMillis() - 14400000, // 4 hours ago
                    status = "Under Review"
                )
            )

            _reportUiState.update { it.copy(recentReports = mockReports) }
        }
    }

    fun updateReportType(type: ReportType) {
        _reportUiState.update { it.copy(reportType = type) }
    }

    fun updateSelectedStation(station: String) {
        _reportUiState.update { it.copy(selectedStation = station) }
    }

    fun updateDescription(description: String) {
        _reportUiState.update { it.copy(description = description) }
    }

    fun updateSeverity(severity: ReportSeverity) {
        _reportUiState.update { it.copy(severity = severity) }
    }

    fun submitReport() {
        val currentState = _reportUiState.value

        if (currentState.selectedStation.isBlank()) {
            _reportUiState.update { it.copy(error = "Please select a station") }
            return
        }

        if (currentState.description.isBlank()) {
            _reportUiState.update { it.copy(error = "Please provide a description") }
            return
        }

        viewModelScope.launch {
            _reportUiState.update { it.copy(isSubmitting = true, error = null) }

            delay(2000) // Simulate submission

            _reportUiState.update {
                it.copy(
                    isSubmitting = false,
                    submitSuccess = true,
                    description = "",
                    selectedStation = ""
                )
            }

            // Reset success state after 3 seconds
            delay(3000)
            _reportUiState.update { it.copy(submitSuccess = false) }

            // Reload reports to show the new one
            loadRecentReports()
        }
    }

    fun clearError() {
        _reportUiState.update { it.copy(error = null) }
    }
}
