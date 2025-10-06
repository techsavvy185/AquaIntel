package com.aquaintel.app.ui.screens.main.report

data class ReportUiState(
    val isLoading: Boolean = false,
    val reportType: ReportType = ReportType.WATER_QUALITY,
    val selectedStation: String = "",
    val description: String = "",
    val severity: ReportSeverity = ReportSeverity.LOW,
    val imageUri: String? = null,
    val isSubmitting: Boolean = false,
    val submitSuccess: Boolean = false,
    val error: String? = null,
    val recentReports: List<CommunityReport> = emptyList()
)

enum class ReportType(val displayName: String) {
    WATER_QUALITY("Water Quality Issue"),
    EQUIPMENT_FAILURE("Equipment Failure"),
    CONTAMINATION("Water Contamination"),
    UNUSUAL_READING("Unusual Reading"),
    OTHER("Other Issue")
}

enum class ReportSeverity(val displayName: String) {
    LOW("Low Priority"),
    MEDIUM("Medium Priority"),
    HIGH("High Priority"),
    CRITICAL("Critical")
}

data class CommunityReport(
    val id: String,
    val type: ReportType,
    val severity: ReportSeverity,
    val description: String,
    val station: String,
    val timestamp: Long,
    val status: String = "Under Review"
)
