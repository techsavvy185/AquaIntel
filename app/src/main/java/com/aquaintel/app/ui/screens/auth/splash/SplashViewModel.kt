package com.aquaintel.app.ui.screens.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState: StateFlow<SplashUiState> = _splashUiState.asStateFlow()

    fun checkAuthStatus(
        onNavigateToOnboarding: () -> Unit,
        onNavigateToDashboard: () -> Unit
    ) {
        viewModelScope.launch {
            delay(2000) // Simulate splash delay

            // Mock: Check if user has completed onboarding
            val hasCompletedOnboarding = false // Hardcoded for demo
            val isLoggedIn = false // Hardcoded for demo

            if (!hasCompletedOnboarding) {
                onNavigateToOnboarding()
            } else if (isLoggedIn) {
                onNavigateToDashboard()
            } else {
                onNavigateToOnboarding()
            }
        }
    }
}
