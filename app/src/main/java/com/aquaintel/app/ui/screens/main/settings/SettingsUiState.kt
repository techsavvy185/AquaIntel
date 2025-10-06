package com.aquaintel.app.ui.screens.main.settings

data class SettingsUiState(
    val userName: String = "Guest User",
    val userEmail: String = "user@example.com",
    val isDarkMode: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val dataRefreshInterval: RefreshInterval = RefreshInterval.HOURLY,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class RefreshInterval(val displayName: String, val minutes: Int) {
    REALTIME("Real-time", 1),
    EVERY_15_MIN("Every 15 minutes", 15),
    HOURLY("Hourly", 60),
    DAILY("Daily", 1440)
}
