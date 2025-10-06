package com.aquaintel.app.ui.screens.main.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState: StateFlow<SettingsUiState> = _settingsUiState.asStateFlow()

    init {
        loadUserSettings()
    }

    private fun loadUserSettings() {
        // Mock user data
        _settingsUiState.update {
            it.copy(
                userName = "Dr. Rajesh Kumar",
                userEmail = "rajesh.kumar@example.com"
            )
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        _settingsUiState.update { it.copy(isDarkMode = enabled) }
        // In real app: Save to DataStore
    }

    fun toggleNotifications(enabled: Boolean) {
        _settingsUiState.update { it.copy(notificationsEnabled = enabled) }
        // In real app: Save to DataStore
    }

    fun updateRefreshInterval(interval: RefreshInterval) {
        _settingsUiState.update { it.copy(dataRefreshInterval = interval) }
        // In real app: Save to DataStore
    }

    fun logout(onLogoutComplete: () -> Unit) {
        // Mock logout
        onLogoutComplete()
    }
}
