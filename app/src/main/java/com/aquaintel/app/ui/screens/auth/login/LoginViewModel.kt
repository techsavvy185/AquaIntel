package com.aquaintel.app.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun emailInput(input: String) {
        _loginUiState.update { it.copy(email = input) }
    }

    fun passwordInput(input: String) {
        _loginUiState.update { it.copy(password = input) }
    }

    fun clearError() {
        _loginUiState.update { it.copy(toastMessage = null) }
    }

    fun onLoginClicked(onNavigateToDashboard: () -> Unit) {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _loginUiState.update { it.copy(toastMessage = "Email and password cannot be empty") }
            return
        }

        _loginUiState.update { it.copy(isLoading = true, toastMessage = null) }

        viewModelScope.launch {
            delay(1500) // Simulate network call

            // Mock login - always succeeds for demo
            _loginUiState.update {
                it.copy(isLoading = false, toastMessage = "Login successful")
            }
            delay(500)
            onNavigateToDashboard()
        }
    }
}
